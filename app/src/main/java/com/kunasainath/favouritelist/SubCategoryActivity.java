package com.kunasainath.favouritelist;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kunasainath.favouritelist.adapters.SubCategoryAdapter;

public class SubCategoryActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private FloatingActionButton addSubCategory;
    private SubCategoryAdapter adapter;

    Category category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);

        initializeViews();

        category = (Category) getIntent().getSerializableExtra(MainActivity.CATEGORY_KEY);
        setTitle(category.getCategory());
        adapter = new SubCategoryAdapter(category);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        addSubCategory.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                displayAlertDialog();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void displayAlertDialog(){
        final EditText edtSubCategory = new EditText(this);
        edtSubCategory.setInputType(InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        edtSubCategory.setBackground(getDrawable(R.drawable.edittext_style));
        new AlertDialog.Builder(this)
                .setTitle(R.string.enter_sub_category)
                .setView(edtSubCategory)
                .setCancelable(false)
                .setPositiveButton(R.string.create, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String subCategoryName = edtSubCategory.getText().toString();
                        category.getSubCategories().add(subCategoryName);

                        SubCategoryAdapter adapter = (SubCategoryAdapter) mRecyclerView.getAdapter();
                        adapter.notifyItemInserted(category.getSubCategories().size()-1);
                        dialogInterface.dismiss();
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).create().show();

    }

    public void initializeViews(){
        mRecyclerView = findViewById(R.id.sub_categories);
        addSubCategory = findViewById(R.id.add_sub_category);
    }

    @Override
    public void onBackPressed() {
        Bundle bundle = new Bundle();
        bundle.putSerializable(MainActivity.CATEGORY_KEY, category);

        Intent intent = new Intent();
        intent.putExtras(bundle);

        setResult(Activity.RESULT_OK, intent);

        super.onBackPressed();
    }
}