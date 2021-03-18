package com.axeedo.mewallet.TransactionFragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import com.axeedo.mewallet.Database.Transaction;
import com.axeedo.mewallet.OnSwitchFragmentListener;
import com.axeedo.mewallet.R;
import com.axeedo.mewallet.TransactionListAdapter;
import com.axeedo.mewallet.TransactionsViewModel;
import com.axeedo.mewallet.Utils.ItemClickSupport;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A fragment representing a list of Transactions.
 */
public class TransactionListFragment extends Fragment {

    private OnSwitchFragmentListener mParentListener;
    //private TransactionsViewModel mTransactionsViewModel;
    TransactionListAdapter mAdapter;
    RecyclerView mTransactionListRecyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TransactionListFragment() { }

    public static TransactionListFragment newInstance(int columnCount) {
        TransactionListFragment fragment = new TransactionListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.transaction_list_fragment, container, false);
        Context context = view.getContext();
        mAdapter = new TransactionListAdapter(getContext());

        // Set TransactionViewModel as the source of data for TransactionListAdapter
        attachViewModelToAdapter(mAdapter);

        // Attach adapter to RecyclerView
        attachAdapterToList(view);

        // Add new transaction button
        Button addTransactionBtn = view.findViewById(R.id.add_transaction_btn);
        addTransactionBtn.setOnClickListener((View v) -> {
            //Redirect to new transaction fragment
            mParentListener = (OnSwitchFragmentListener) getContext();
            mParentListener.goToFragment(NewTransactionFragment.class, null);
        });

        return view;
    }


    /**
     * Sets {@link TransactionsViewModel} as the source of data for the provided {@link TransactionListAdapter}
     * @param adapter
     */
    void attachViewModelToAdapter(@NotNull TransactionListAdapter adapter) {
        //get the ViewModel that should have been instantiated in parent activity's onCreate() method
        TransactionsViewModel transactionsViewModel = new ViewModelProvider(requireActivity())
                .get(TransactionsViewModel.class);

        // Since we are using LiveData, we can observe the changes to the data and update the view
        // when it is changed
        transactionsViewModel.getAllTransactions()
                .observe(getViewLifecycleOwner(), adapter::setTransactions);
    }

    /**
     * Initialises transactionList RecyclerView and attaches it to mAdapter
     * @param view
     */
    void attachAdapterToList(View view) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        mTransactionListRecyclerView = (RecyclerView) view.findViewById(R.id.transaction_list);
        mTransactionListRecyclerView.setLayoutManager(linearLayoutManager);
        mTransactionListRecyclerView.setAdapter(mAdapter);
        ItemClickSupport.addTo(mTransactionListRecyclerView)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {


                        Bundle args = new Bundle();
                        args.putInt("selected_transaction_position", position);
                        mParentListener = (OnSwitchFragmentListener) getContext();
                        mParentListener.goToFragment(TransactionDetailFragment.class, args);
                    }
                });
    }
}