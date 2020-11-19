package com.kunasainath.favouritelist.view_holders;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.kunasainath.favouritelist.R;

public class SubCategoryViewHolder extends RecyclerView.ViewHolder{
    private TextView subCategoryName;
    public SubCategoryViewHolder(View view){
        super(view);

        subCategoryName = view.findViewById(R.id.category);
    }

    public TextView getSubCategoryName() {
        return subCategoryName;
    }
}
