package com.axeedo.mewallet.TransactionFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.axeedo.mewallet.Database.AppDatabase;
import com.axeedo.mewallet.Database.Repository;
import com.axeedo.mewallet.OnSwitchFragmentListener;
import com.axeedo.mewallet.R;
import com.axeedo.mewallet.Database.Transaction;
import com.axeedo.mewallet.TransactionsViewModel;

public class NewTransactionFragment extends Fragment
        implements TransactionEditorFragment.OnUpdatedTransactionDataListener {

    OnSwitchFragmentListener parentListener;
    public NewTransactionFragment() { /* Required empty public constructor */ }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        getChildFragmentManager().beginTransaction()
                .add(R.id.new_transaction_editor,TransactionEditorFragment.class, null)
                .commit();
        return inflater.inflate(R.layout.transaction_new_fragment, container, false);
    }

    @Override
    public void newTransactionNotification(Transaction newTransaction) {
        //Update database
        new ViewModelProvider(requireActivity()).get(TransactionsViewModel.class)
                .insert(newTransaction);

        //Redirect to transaction list
        parentListener = (OnSwitchFragmentListener) getContext();
        parentListener.goToFragment(TransactionListFragment.class, null);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        parentListener = null;
    }
}