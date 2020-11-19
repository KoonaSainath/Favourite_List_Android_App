package com.kunasainath.favouritelist;

import java.io.Serializable;
import java.util.ArrayList;

public class Category implements Serializable {
    private String Category;
    private ArrayList<String> subCategories;

    public Category(String category, ArrayList<String> subCategories) {
        Category = category;
        this.subCategories = subCategories;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public ArrayList<String> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(ArrayList<String> subCategories) {
        this.subCategories = subCategories;
    }
}
