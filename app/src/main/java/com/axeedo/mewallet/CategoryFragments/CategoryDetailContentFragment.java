package com.axeedo.mewallet.CategoryFragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.axeedo.mewallet.Database.Category;
import com.axeedo.mewallet.Database.Transaction;
import com.axeedo.mewallet.R;
import com.axeedo.mewallet.TransactionFragments.TransactionDetailContentFragment;
import com.axeedo.mewallet.TransactionFragments.TransactionEditorFragment;
import com.axeedo.mewallet.Utils.Constants;
import com.axeedo.mewallet.ViewModels.appViewModel;


public class CategoryDetailContentFragment extends Fragment {

    OnEditCategoryListener parentListener;
    private int mPosition;
    private Category mCategory;
    public CategoryDetailContentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPosition = getArguments().getInt(Constants.ARG_POSITION);
            appViewModel appViewModel = new ViewModelProvider(requireActivity())
                    .get(appViewModel.class);
            mCategory = appViewModel.getCategory(mPosition);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.category_detail_content_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setViewFromCategory(view);

        // Add edit transaction button
        Button editCategoryBtn = view.findViewById(R.id.edit_category_button);
        editCategoryBtn.setOnClickListener((View v) -> {
            //Redirect to edit transaction fragment
            parentListener.goToEditFragment(mPosition);
        });
    }

    private void setViewFromCategory(View view) {
        TextView name = view.findViewById(R.id.category_name);

        appViewModel appViewModel = new ViewModelProvider(requireActivity())
                .get(appViewModel.class);
        mCategory = appViewModel.getCategory(mPosition);
        if (mCategory == null){
            mCategory = new Category("No category found");
            // TODO do not allow edit
        }

        name.setText(mCategory.getName());
    }

    public interface OnEditCategoryListener{
        public void goToEditFragment(int categoryPosition);
    }

    // get parent fragment
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // check if parent Fragment implements listener
        if (getParentFragment() instanceof CategoryEditorFragment.OnUpdatedCategoryDataListener) {
            parentListener = (OnEditCategoryListener) getParentFragment();
        } else {
            throw new RuntimeException("The parent fragment must implement " +
                    "OnEditCategoryListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        parentListener = null;
    }
}