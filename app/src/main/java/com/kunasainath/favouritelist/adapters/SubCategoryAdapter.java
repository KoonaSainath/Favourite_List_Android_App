package com.kunasainath.favouritelist.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kunasainath.favouritelist.Category;
import com.kunasainath.favouritelist.R;
import com.kunasainath.favouritelist.view_holders.SubCategoryViewHolder;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryViewHolder>{

    private Category category;

    public SubCategoryAdapter(Category category){
        this.category = category;
    }

    @NonNull
    @Override
    public SubCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.each_category, parent, false);
        return new SubCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoryViewHolder holder, int position) {
        holder.getSubCategoryName().setText(category.getSubCategories().get(position));
    }

    @Override
    public int getItemCount() {
        return category.getSubCategories().size();
    }

    public void setCategory(Category category){
        this.category = category;
    }
}
