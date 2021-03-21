package com.axeedo.mewallet.CategoryFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.axeedo.mewallet.Database.Category;
import com.axeedo.mewallet.Database.Transaction;
import com.axeedo.mewallet.R;
import com.axeedo.mewallet.TransactionFragments.TransactionDetailContentFragment;
import com.axeedo.mewallet.TransactionFragments.TransactionEditorFragment;
import com.axeedo.mewallet.TransactionFragments.TransactionListFragment;
import com.axeedo.mewallet.Utils.Constants;
import com.axeedo.mewallet.Utils.OnSwitchFragmentListener;
import com.axeedo.mewallet.ViewModels.appViewModel;


public class CategoryDetailFragment extends Fragment
        implements CategoryDetailContentFragment.OnEditCategoryListener, CategoryEditorFragment.OnUpdatedCategoryDataListener {

    private int mPosition;
    private OnSwitchFragmentListener mParentListener;

    public CategoryDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPosition = getArguments().getInt(Constants.ARG_POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.category_detail_fragment, container, false);
        Bundle args = new Bundle();
        args.putInt(Constants.ARG_POSITION, mPosition);
        getChildFragmentManager().beginTransaction()
                .replace(R.id.category_detail_content, CategoryDetailContentFragment.class, args)
                .addToBackStack(Constants.MAIN_BACKSTACK)
                .commit();

        return view;
    }

    @Override
    public void newCategoryNotification(Category newCategory) {
        //Update database
        new ViewModelProvider(requireActivity()).get(appViewModel.class)
                .insert(newCategory);
        Toast.makeText(getContext(),"Category updated", Toast.LENGTH_SHORT).show();

        //Redirect to Category list
        mParentListener = (OnSwitchFragmentListener) getContext();
        mParentListener.goToFragment(CategoryListFragment.class, null);
    }

    @Override
    public void goToEditFragment(int categoryPosition) {
        Bundle args = new Bundle();
        args.putInt(Constants.ARG_POSITION, categoryPosition);
        getChildFragmentManager().beginTransaction()
                .replace(R.id.category_detail_content, CategoryEditorFragment.class, args)
                .commit();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mParentListener = null;
    }
}