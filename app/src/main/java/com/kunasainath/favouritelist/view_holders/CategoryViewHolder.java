package com.kunasainath.favouritelist.view_holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kunasainath.favouritelist.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder{
    private TextView txtCategory;
    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);

        txtCategory = itemView.findViewById(R.id.category);
    }

    public TextView getTxtCategory() {
        return txtCategory;
    }
}
