package com.kunasainath.favouritelist.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kunasainath.favouritelist.Category;
import com.kunasainath.favouritelist.R;
import com.kunasainath.favouritelist.UtilityClass;
import com.kunasainath.favouritelist.adapters.SubCategoryAdapter;

import java.util.Collections;


public class SubCategoryFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private static final String ARGUMENT_KEY = "category argument";
    private Category category;

    public SubCategoryFragment() {
        // Required empty public constructor
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public Category getCategory() {
        return category;
    }

    public static SubCategoryFragment newInstance(Category category) {
        SubCategoryFragment fragment = new SubCategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARGUMENT_KEY, category);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        category = (Category) getArguments().getSerializable(ARGUMENT_KEY);
        Collections.sort(category.getSubCategories(), UtilityClass.getStringSorter());
        mRecyclerView.setAdapter(new SubCategoryAdapter(category));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sub_category, container, false);
        mRecyclerView = view.findViewById(R.id.sub_categories);
        return view;
    }
}