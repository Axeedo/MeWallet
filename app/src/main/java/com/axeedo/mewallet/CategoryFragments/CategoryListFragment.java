package com.axeedo.mewallet.CategoryFragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.axeedo.mewallet.Adapters.CategoryListAdapter;
import com.axeedo.mewallet.Adapters.TransactionListAdapter;
import com.axeedo.mewallet.Utils.OnSwitchFragmentListener;
import com.axeedo.mewallet.R;
import com.axeedo.mewallet.TransactionFragments.NewTransactionFragment;
import com.axeedo.mewallet.Utils.Constants;
import com.axeedo.mewallet.Utils.ItemClickSupport;
import com.axeedo.mewallet.ViewModels.appViewModel;

import org.jetbrains.annotations.NotNull;

/**
 * A fragment representing a list of Categories.
 */
public class CategoryListFragment extends Fragment {

    CategoryListAdapter mAdapter;
    OnSwitchFragmentListener mParentListener;
    RecyclerView mCategoryListRecyclerView;

    public CategoryListFragment() {
        /* Required empty public constructor */
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // get args
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.category_list_fragment, container, false);
        Context context = view.getContext();
        mAdapter = new CategoryListAdapter(getContext());

        // Set appViewModel as the source of data for CategoryListAdapter
        attachViewModelToAdapter(mAdapter);

        // Attach adapter to RecyclerView
        attachAdapterToList(view);

        // Add new Category button
        Button addCategoryBtn = view.findViewById(R.id.add_category_btn);
        addCategoryBtn.setOnClickListener((View v) -> {
            //Redirect to new Category fragment
            mParentListener = (OnSwitchFragmentListener) getContext();
            mParentListener.goToFragment(NewCategoryFragment.class, null);
        });

        return view;
    }


    /**
     * Sets {@link appViewModel} as the source of data for the provided {@link CategoryListAdapter}
     * @param adapter
     */
    void attachViewModelToAdapter(@NotNull CategoryListAdapter adapter) {
        //get the ViewModel that should have been instantiated in parent activity's onCreate() method
        appViewModel appViewModel = new ViewModelProvider(requireActivity())
                .get(appViewModel.class);

        // Since we are using LiveData, we can observe the changes to the data and update the view
        // when it is changed
        appViewModel.getAllCategories()
                .observe(getViewLifecycleOwner(), adapter::setCategories);
    }

    /**
     * Initialises transactionList RecyclerView and attaches it to mAdapter
     * @param view
     */
    void attachAdapterToList(View view) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        // put new entries first
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        // RecyclerView
        mCategoryListRecyclerView = (RecyclerView) view.findViewById(R.id.category_list);
        mCategoryListRecyclerView.setLayoutManager(linearLayoutManager);
        mCategoryListRecyclerView.setAdapter(mAdapter);
        // Add clickListener to item in RecyclerView
        ItemClickSupport.addTo(mCategoryListRecyclerView)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Bundle args = new Bundle();
                        args.putInt(Constants.ARG_POSITION, position);
                        mParentListener = (OnSwitchFragmentListener) getContext();
                        mParentListener.goToFragment(CategoryDetailFragment.class, args);
                    }
                });
    }
}