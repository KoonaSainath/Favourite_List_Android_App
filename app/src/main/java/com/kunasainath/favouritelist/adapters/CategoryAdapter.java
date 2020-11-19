package com.kunasainath.favouritelist.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kunasainath.favouritelist.Category;
import com.kunasainath.favouritelist.R;
import com.kunasainath.favouritelist.view_holders.CategoryViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder>{

    ArrayList<Category> categories;

    public interface AdapterItemClickedInterface {
        public void anItemIsClicked(Category category);
    }

    private AdapterItemClickedInterface clickedInterface;

    public CategoryAdapter(ArrayList<Category> categories, AdapterItemClickedInterface clickedInterface){
        this.categories = categories;
        this.clickedInterface = clickedInterface;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.each_category, parent, false);
        CategoryViewHolder categoryViewHolder = new CategoryViewHolder(view);
        return categoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.getTxtCategory().setText(categories.get(position).getCategory());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedInterface.anItemIsClicked(categories.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void addCategory(Category category){
        categories.add(category);
        Collections.sort(categories, new Comparator<Category>() {
            @Override
            public int compare(Category a, Category b) {
                if(a.getCategory().compareTo(b.getCategory()) > 0){
                    return 1;
                }return -1;
            }
        });
        notifyDataSetChanged();
        notifyItemInserted(categories.size()-1);
    }
}
