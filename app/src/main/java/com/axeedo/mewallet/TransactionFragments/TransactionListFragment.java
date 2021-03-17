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
import android.widget.Button;

import com.axeedo.mewallet.Database.Transaction;
import com.axeedo.mewallet.OnSwitchFragmentListener;
import com.axeedo.mewallet.R;
import com.axeedo.mewallet.TransactionListAdapter;
import com.axeedo.mewallet.TransactionsViewModel;

import java.util.List;

/**
 * A fragment representing a list of Transactions.
 */
public class TransactionListFragment extends Fragment {

    private OnSwitchFragmentListener mParentListener;
    private TransactionsViewModel mTransactionsViewModel;
    TransactionListAdapter mAdapter;
    RecyclerView mTransactionListRecyclerView;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TransactionListFragment() {
    }

    public static TransactionListFragment newInstance(int columnCount) {
        TransactionListFragment fragment = new TransactionListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            //Get arguments here
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.transaction_list_fragment, container, false);

        Context context = view.getContext();
        mAdapter = new TransactionListAdapter(getContext());

        //TransactionViewModel
        mTransactionsViewModel = new ViewModelProvider(requireActivity()).get(TransactionsViewModel.class);
        mTransactionsViewModel.getAllTransactions().observe(getViewLifecycleOwner(), new Observer<List<Transaction>>() {
            @Override
            public void onChanged(List<Transaction> transactions) {
                mAdapter.setTransactions(transactions);
            }
        });

        mTransactionListRecyclerView = (RecyclerView) view.findViewById(R.id.transaction_list);
        mTransactionListRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mTransactionListRecyclerView.setAdapter(mAdapter);
        Log.i("JFL","Just attached adapter");

        // Set the adapter
        if (view instanceof RecyclerView) {

        }

        Button addTransactionBtn = view.findViewById(R.id.add_transaction_btn);
        addTransactionBtn.setOnClickListener((View v) -> {
            //Redirect to new transaction fragment
            mParentListener = (OnSwitchFragmentListener) getContext();
            mParentListener.goToFragment(NewTransactionFragment.class, null);
        });

        return view;
    }

    // TODO store in ViewModel
    /*public List<Transaction> getTransactions(){
        return AppDatabase.getDbInstance(getContext()).transactionDAO().getAll();
    }*/
}