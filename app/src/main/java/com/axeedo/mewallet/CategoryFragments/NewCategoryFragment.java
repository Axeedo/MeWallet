package com.axeedo.mewallet.CategoryFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.axeedo.mewallet.Database.Category;
import com.axeedo.mewallet.R;
import com.axeedo.mewallet.TransactionFragments.TransactionEditorFragment;
import com.axeedo.mewallet.TransactionFragments.TransactionListFragment;
import com.axeedo.mewallet.Utils.OnSwitchFragmentListener;
import com.axeedo.mewallet.ViewModels.appViewModel;

import java.util.Locale;


public class NewCategoryFragment extends Fragment
        implements CategoryEditorFragment.OnUpdatedCategoryDataListener {

    OnSwitchFragmentListener parentListener;

    public NewCategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getChildFragmentManager().beginTransaction()
                .add(R.id.new_category_editor, CategoryEditorFragment.class, null)
                .commit();
        return inflater.inflate(R.layout.category_new_fragment, container, false);
    }

    @Override
    public void newCategoryNotification(Category newCategory) {
        //Update database
        new ViewModelProvider(requireActivity()).get(appViewModel.class)
                .insert(newCategory);

        //Redirect to transaction list
        parentListener = (OnSwitchFragmentListener) getContext();
        parentListener.goToFragment(CategoryListFragment.class, null);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        parentListener = null;
    }
}