package com.axeedo.mewallet.TransactionFragments;

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
import android.widget.EditText;
import android.widget.TextView;

import com.axeedo.mewallet.Database.Transaction;
import com.axeedo.mewallet.OnSwitchFragmentListener;
import com.axeedo.mewallet.R;
import com.axeedo.mewallet.TransactionsViewModel;
import com.axeedo.mewallet.Utils.Constants;

import java.util.Locale;

public class TransactionDetailContentFragment extends Fragment {

    OnEditTransactionListener parentListener;
    private int mPosition;
    private Transaction mTransaction;

    public TransactionDetailContentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPosition = getArguments().getInt(Constants.ARG_POSITION);
            TransactionsViewModel transactionsViewModel = new ViewModelProvider(requireActivity())
                    .get(TransactionsViewModel.class);
            mTransaction = transactionsViewModel.getTransaction(mPosition);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.transaction_detail_content_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setViewFromTransaction(view);

        // Add edit transaction button
        Button editTransactionBtn = view.findViewById(R.id.edit_transaction_button);
        editTransactionBtn.setOnClickListener((View v) -> {
            //Redirect to edit transaction fragment
            parentListener.goToEditFragment(mPosition);
        });
    }

    void setViewFromTransaction(View view) {
        TextView name = view.findViewById(R.id.transaction_name);
        TextView value = view.findViewById(R.id.transaction_value);

        TransactionsViewModel transactionsViewModel = new ViewModelProvider(requireActivity())
                .get(TransactionsViewModel.class);
        mTransaction = transactionsViewModel.getTransaction(mPosition);
        if (mTransaction == null){
            mTransaction = new Transaction("No transaction found", 0.0);
            // TODO do not allow edit
        }

        name.setText(mTransaction.getName());
        value.setText(String.format(Locale.US,"%.2f", mTransaction.getValue()));
    }

    public interface OnEditTransactionListener{
        public void goToEditFragment(int transactionPosition);
    }

    // get parent fragment
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // check if parent Fragment implements listener
        if (getParentFragment() instanceof TransactionEditorFragment.OnUpdatedTransactionDataListener) {
            parentListener = (OnEditTransactionListener) getParentFragment();
        } else {
            throw new RuntimeException("The parent fragment must implement " +
                    "OnEditTransactionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        parentListener = null;
    }
}