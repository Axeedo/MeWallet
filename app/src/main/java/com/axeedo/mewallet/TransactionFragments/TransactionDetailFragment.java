package com.axeedo.mewallet.TransactionFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.axeedo.mewallet.Database.Repository;
import com.axeedo.mewallet.Database.Transaction;
import com.axeedo.mewallet.OnSwitchFragmentListener;
import com.axeedo.mewallet.R;
import com.axeedo.mewallet.TransactionsViewModel;
import com.axeedo.mewallet.Utils.Constants;

import java.util.Locale;


public class TransactionDetailFragment extends Fragment
        implements TransactionEditorFragment.OnUpdatedTransactionDataListener, TransactionDetailContentFragment.OnEditTransactionListener {

    private int mPosition;
    private OnSwitchFragmentListener mParentListener;

    public TransactionDetailFragment() { /* Required empty public constructor */ }

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
        View view = inflater.inflate(R.layout.transaction_detail_fragment, container, false);
        Bundle args = new Bundle();
        args.putInt(Constants.ARG_POSITION, mPosition);
        getChildFragmentManager().beginTransaction()
                .replace(R.id.transaction_detail_content,TransactionDetailContentFragment.class, args)
                .addToBackStack(Constants.MAIN_BACKSTACK)
                .commit();

        return view;
    }

    @Override
    public void newTransactionNotification(Transaction newTransaction) {
        //Update database
        new ViewModelProvider(requireActivity()).get(TransactionsViewModel.class)
                .insert(newTransaction);
        Toast.makeText(getContext(),"Transaction updated", Toast.LENGTH_SHORT).show();

        //Redirect to transaction list
        mParentListener = (OnSwitchFragmentListener) getContext();
        mParentListener.goToFragment(TransactionListFragment.class, null);
    }

    @Override
    public void goToEditFragment(int transactionPosition) {
        Bundle args = new Bundle();
        args.putInt(Constants.ARG_POSITION, transactionPosition);
        getChildFragmentManager().beginTransaction()
                .replace(R.id.transaction_detail_content,TransactionEditorFragment.class, args)
                .commit();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mParentListener = null;
    }
}