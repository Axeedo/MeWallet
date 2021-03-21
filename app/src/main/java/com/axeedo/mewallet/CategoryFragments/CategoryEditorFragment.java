package com.axeedo.mewallet.CategoryFragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.axeedo.mewallet.Database.Category;
import com.axeedo.mewallet.Database.Transaction;
import com.axeedo.mewallet.R;
import com.axeedo.mewallet.TransactionFragments.TransactionEditorFragment;
import com.axeedo.mewallet.Utils.Constants;
import com.axeedo.mewallet.ViewModels.appViewModel;

import java.util.Locale;

public class CategoryEditorFragment extends Fragment {

    OnUpdatedCategoryDataListener parentListener;
    private int mPosition;
    private Category mCategory;
    public CategoryEditorFragment() {
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
        return inflater.inflate(R.layout.category_editor_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(mCategory != null){
            setViewFromCategory(view);
        }

        Button saveBtn = getView().findViewById(R.id.save_category_button);
        saveBtn.setOnClickListener( (View v)->{
            Category updatedCategory = getCategoryFromView(view);

            // Let Parent fragment (or any class implementing OnUpdatedTransactionDataListener)
            // decide what to do with the new Transaction object
            parentListener.newCategoryNotification(updatedCategory);
        });
    }

    void setViewFromCategory(View v) {
        EditText nameInput = v.findViewById(R.id.new_category_name);
        //Todo handle category selection

        nameInput.setText(mCategory.getName());
    }

    Category getCategoryFromView(View v) {
        EditText nameInput = v.findViewById(R.id.new_category_name);
        //Todo handle category selection

        String name = nameInput.getText().toString();
        if(mCategory == null) {
            Log.i("JFL", "New category: " + name);
            return new Category(name);
        } else{
            mCategory.setName(name);
            return mCategory;
        }
    }

    /**
     * This interface is used to notify the parent of a new or an updated transaction
     */
    public interface OnUpdatedCategoryDataListener {
        void newCategoryNotification(Category newCategory);
    }

    // get parent fragment
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // check if parent Fragment implements listener
        if (getParentFragment() instanceof OnUpdatedCategoryDataListener) {
            parentListener = (OnUpdatedCategoryDataListener) getParentFragment();
        } else {
            throw new RuntimeException("The parent fragment must implement " +
                    "OnUpdatedCategoryDataListener");
        }
    }
}