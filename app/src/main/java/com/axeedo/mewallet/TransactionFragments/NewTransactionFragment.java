package com.axeedo.mewallet.TransactionFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.axeedo.mewallet.Database.AppDatabase;
import com.axeedo.mewallet.R;
import com.axeedo.mewallet.Database.Transaction;

public class NewTransactionFragment extends Fragment implements TransactionEditorFragment.OnUpdatedTransactionData {

    private OnNewTransaction parentListener;
    public static NewTransactionFragment newInstance() {
        return new NewTransactionFragment();
    }

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
        AppDatabase db = AppDatabase.getDbInstance(getContext());
        db.transactionDAO().insertAll(newTransaction);

        //Redirect to transaction list
        parentListener = (OnNewTransaction) getContext();
        parentListener.switchFragment();
    }

    public interface OnNewTransaction {
        public void switchFragment();
    }
}