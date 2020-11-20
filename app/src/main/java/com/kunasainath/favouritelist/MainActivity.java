package com.kunasainath.favouritelist;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kunasainath.favouritelist.adapters.CategoryAdapter;
import com.kunasainath.favouritelist.adapters.SubCategoryAdapter;
import com.kunasainath.favouritelist.fragments.CategoryFragment;
import com.kunasainath.favouritelist.fragments.SubCategoryFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity implements CategoryFragment.CategoryFragmentInterface {

    private FloatingActionButton fabAddCategory;

    private boolean isTablet = false;

    private CategoryFragment mCategoryFragment = CategoryFragment.newInstance();
    private SubCategoryFragment mSubCategoryFragment;
    private FrameLayout subCategoryFragmentContainer;

    public static final String CATEGORY_KEY = "Category key";
    public static final int ACTIVITY_REQUEST_KEY = 7;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();

        subCategoryFragmentContainer = findViewById(R.id.sub_category_fragment_container);

        isTablet = (subCategoryFragmentContainer != null);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.category_fragment_container, mCategoryFragment)
                .commit();

        fabAddCategory.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                createAlertDialog();
            }
        });
    }
    private void initializeViews(){
        fabAddCategory = findViewById(R.id.addcategory);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void createAlertDialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(getString(R.string.enter_category));
        EditText enterCategory = new EditText(this);
        enterCategory.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        enterCategory.setBackground(getDrawable(R.drawable.edittext_style));
        alert.setView(enterCategory);
        alert.setCancelable(false);
        alert.setPositiveButton(getString(R.string.create), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String categoryName = enterCategory.getText().toString();
                Category category = new Category(categoryName,new ArrayList());
                if(categoryName.length()<=0) {
                    Toast.makeText(MainActivity.this, getString(R.string.invalid_category), Toast.LENGTH_SHORT).show();
                    return;
                }
                mCategoryFragment.getAdapter().addCategory(category);

                mCategoryFragment.getPreferenceManager().addCategoryToSharedPreferences(category);
                Toast.makeText(MainActivity.this, getString(R.string.success), Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alert.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menu_add_category){
            createAlertDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void moveToSubCategory(Category category){
        if(isTablet){

            if(mSubCategoryFragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .remove(mSubCategoryFragment).
                        commit();

                mSubCategoryFragment = null;
            }

            mSubCategoryFragment = SubCategoryFragment.newInstance(category);

            setTitle(category.getCategory());
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.sub_category_fragment_container, mSubCategoryFragment)
                    .addToBackStack(null)
                    .commit();

            fabAddCategory.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View view) {
                    displayAlertDialogForSubCategory();
                }
            });

        }else {
            Intent intent = new Intent(MainActivity.this, SubCategoryActivity.class);
            intent.putExtra(CATEGORY_KEY, category);
            startActivityForResult(intent, ACTIVITY_REQUEST_KEY);
        }
    }

    @Override
    public void onBackPressed() {

        if(mSubCategoryFragment != null) {

            setTitle(getString(R.string.app_name));
            getSupportFragmentManager()
                    .beginTransaction()
                    .remove(mSubCategoryFragment)
                    .commit();
        }

        fabAddCategory.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                createAlertDialog();
            }
        });

        super.onBackPressed();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ACTIVITY_REQUEST_KEY && resultCode == Activity.RESULT_OK && data != null){
            Category category = (Category) data.getSerializableExtra(CATEGORY_KEY);
            mCategoryFragment.getPreferenceManager().addCategoryToSharedPreferences(category);
            updateCategories();
        }
    }

    void updateCategories(){
        ArrayList<Category> categories = mCategoryFragment.getPreferenceManager().getAllCategoriesFromSharedPreferences();
        Collections.sort(categories, UtilityClass.getSorter());
        CategoryAdapter adapter = new CategoryAdapter(categories, mCategoryFragment);
        mCategoryFragment.getRecyclerView().setAdapter(adapter);
    }


    @Override
    public void fragmentCategoryTapped(Category category) {
        moveToSubCategory(category);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void displayAlertDialogForSubCategory(){
        final EditText edtSubCategory = new EditText(this);
        edtSubCategory.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        edtSubCategory.setBackground(getDrawable(R.drawable.edittext_style));
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle(R.string.enter_sub_category)
                .setView(edtSubCategory)
                .setCancelable(false)
                .setPositiveButton(R.string.create, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String subCategoryName = edtSubCategory.getText().toString();
                        Category category = mSubCategoryFragment.getCategory();
                        category.getSubCategories().add(subCategoryName);
                        Collections.sort(mSubCategoryFragment.getCategory().getSubCategories(), UtilityClass.getStringSorter());

                        mCategoryFragment.getPreferenceManager().addCategoryToSharedPreferences(category);

                        SubCategoryAdapter adapter = (SubCategoryAdapter) mSubCategoryFragment.getRecyclerView().getAdapter();
                        adapter.setCategory(category);
                        adapter.notifyDataSetChanged();
                        dialogInterface.dismiss();
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).create().show();
    }
}