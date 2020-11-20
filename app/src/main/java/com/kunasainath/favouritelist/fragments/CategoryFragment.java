package com.kunasainath.favouritelist.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kunasainath.favouritelist.Category;
import com.kunasainath.favouritelist.CategoryPreferenceManager;
import com.kunasainath.favouritelist.R;
import com.kunasainath.favouritelist.UtilityClass;
import com.kunasainath.favouritelist.adapters.CategoryAdapter;

import java.util.ArrayList;
import java.util.Collections;

public class CategoryFragment extends Fragment implements CategoryAdapter.AdapterItemClickedInterface{

    private RecyclerView mRecyclerView;
    private CategoryPreferenceManager preferenceManager;
    private CategoryAdapter adapter;
    private ArrayList<Category> categories;
    private CategoryFragmentInterface mCategoryFragmentInterface;

    public interface CategoryFragmentInterface{
        public void fragmentCategoryTapped(Category category);
    }

    public CategoryFragment() {
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public CategoryPreferenceManager getPreferenceManager() {
        return preferenceManager;
    }

    public CategoryAdapter getAdapter() {
        return adapter;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        preferenceManager = new CategoryPreferenceManager(context);
        mCategoryFragmentInterface = (CategoryFragmentInterface) context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRecyclerView = getView().findViewById(R.id.maincategories);

        categories = preferenceManager.getAllCategoriesFromSharedPreferences();
        Collections.sort(categories, UtilityClass.getSorter());

        adapter = new CategoryAdapter(categories, this);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public static CategoryFragment newInstance() {
        return new CategoryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void anItemIsClicked(Category category) {
        mCategoryFragmentInterface.fragmentCategoryTapped(category);
    }
}