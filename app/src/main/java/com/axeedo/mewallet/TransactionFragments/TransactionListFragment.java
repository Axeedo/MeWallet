package com.axeedo.mewallet.TransactionFragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.axeedo.mewallet.Database.AppDatabase;
import com.axeedo.mewallet.Database.Transaction;
import com.axeedo.mewallet.R;
import com.axeedo.mewallet.dummy.DummyContent;
import com.axeedo.mewallet.transactionListAdapter;

import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class TransactionListFragment extends Fragment {


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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.transaction_list_fragment, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new transactionListAdapter(getTransactions()));
        }
        return view;
    }

    public List<Transaction> getTransactions(){
        return AppDatabase.getDbInstance(getContext()).transactionDAO().getAll();
    }
}