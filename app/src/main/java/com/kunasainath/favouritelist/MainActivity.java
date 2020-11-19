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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kunasainath.favouritelist.adapters.CategoryAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity implements CategoryAdapter.AdapterItemClickedInterface {
    private RecyclerView mRecyclerView;
    private FloatingActionButton fabAddCategory;
    private CategoryAdapter adapter;
    private ArrayList<Category> categories;
    private CategoryPreferenceManager preferenceManager;
    public static final String CATEGORY_KEY = "Category key";
    public static final int ACTIVITY_REQUEST_KEY = 7;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();

        preferenceManager = new CategoryPreferenceManager(this);

        categories = preferenceManager.getAllCategoriesFromSharedPreferences();
        Collections.sort(categories, new Comparator<Category>() {
            @Override
            public int compare(Category a, Category b) {
                if(a.getCategory().compareTo(b.getCategory()) > 0){
                    return 1;
                }return -1;
            }
        });

        adapter = new CategoryAdapter(categories , MainActivity.this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
        fabAddCategory.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                createAlertDialog();
            }
        });
    }
    private void initializeViews(){
        mRecyclerView = findViewById(R.id.maincategories);
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
                adapter.addCategory(category);

                preferenceManager.addCategoryToSharedPreferences(category);
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
        Intent intent = new Intent(MainActivity.this, SubCategoryActivity.class);
        intent.putExtra(CATEGORY_KEY, category);
        startActivityForResult(intent, ACTIVITY_REQUEST_KEY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ACTIVITY_REQUEST_KEY && resultCode == Activity.RESULT_OK && data != null){
            Category category = (Category) data.getSerializableExtra(CATEGORY_KEY);
            preferenceManager.addCategoryToSharedPreferences(category);
            updateCategories();
        }
    }

    void updateCategories(){
        ArrayList<Category> categories = preferenceManager.getAllCategoriesFromSharedPreferences();
        Collections.sort(categories, new Comparator<Category>() {
            @Override
            public int compare(Category a, Category b) {
                if(a.getCategory().compareTo(b.getCategory()) > 1){
                    return 1;
                }return -1;
            }
        });
        CategoryAdapter adapter = new CategoryAdapter(categories, this);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void anItemIsClicked(Category category) {
        moveToSubCategory(category);
    }
}