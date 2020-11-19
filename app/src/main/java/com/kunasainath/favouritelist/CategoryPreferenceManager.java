package com.kunasainath.favouritelist;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class CategoryPreferenceManager {
    private Context context;
    public CategoryPreferenceManager(Context context){
        this.context = context;
    }
    public void addCategoryToSharedPreferences(Category category){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putStringSet(category.getCategory(), new LinkedHashSet(category.getSubCategories()));
        editor.apply();
    }
    public ArrayList<Category> getAllCategoriesFromSharedPreferences(){

        ArrayList<Category> categories = new ArrayList<Category>();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        HashMap<String,?> categoriesMap = (HashMap<String, ?>) preferences.getAll();

        Set set = categoriesMap.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()){
            Map.Entry<String,?> entry = (Map.Entry<String,?>) iterator.next();
            Category category = new Category(entry.getKey(), new ArrayList<String>((HashSet) entry.getValue()));
            categories.add(category);
        }

        return categories;
    }
}
