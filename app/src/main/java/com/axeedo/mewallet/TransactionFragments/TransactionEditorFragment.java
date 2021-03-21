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

import com.axeedo.mewallet.Database.Transaction;
import com.axeedo.mewallet.R;
import com.axeedo.mewallet.ViewModels.appViewModel;
import com.axeedo.mewallet.Utils.Constants;

import java.util.Locale;


public class TransactionEditorFragment extends Fragment {

    OnUpdatedTransactionDataListener parentListener;
    private int mPosition;
    private Transaction mTransaction;

    public TransactionEditorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TransactionEditorFragment.
     */
    public static TransactionEditorFragment newInstance() {
        return new TransactionEditorFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            mPosition = getArguments().getInt(Constants.ARG_POSITION);
            appViewModel appViewModel = new ViewModelProvider(requireActivity())
                    .get(appViewModel.class);
            mTransaction = appViewModel.getTransaction(mPosition);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.transaction_editor_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(mTransaction != null){
            setViewFromTransaction(view);
        }

        Button saveBtn = getView().findViewById(R.id.save_transaction_button);
        saveBtn.setOnClickListener( (View v)->{
            Transaction updatedTransaction = getTransactionFromView(view);

            // Let Parent fragment (or any class implementing OnUpdatedTransactionDataListener)
            // decide what to do with the new Transaction object
            parentListener.newTransactionNotification(updatedTransaction);
        });
    }

    void setViewFromTransaction(View v) {
        EditText nameInput = v.findViewById(R.id.new_transaction_name);
        EditText valueInput = v.findViewById(R.id.new_transaction_value);
        //Todo handle category selection

        nameInput.setText(mTransaction.getName());

        //Locale is here because Android lint is showing a warning.
        valueInput.setText(String.format(Locale.US,"%.2f", mTransaction.getValue()));
    }

    Transaction getTransactionFromView(View v) {
        EditText nameInput = v.findViewById(R.id.new_transaction_name);
        EditText valueInput = v.findViewById(R.id.new_transaction_value);
        //Todo handle category selection

        String name = nameInput.getText().toString();
        double value = 0.0;
        try{
            value = Double.parseDouble(valueInput.getText().toString());
        }catch (NumberFormatException e) {
            //TODO refuse save
        }
        if(mTransaction == null)
            return new Transaction(name, value);
        else{
            mTransaction.setName(name);
            mTransaction.setValue(value);
            return mTransaction;
        }
    }

    /**
     * This interface is used to notify the parent of a new or an updated transaction
     */
    public interface OnUpdatedTransactionDataListener {
        void newTransactionNotification(Transaction newTransaction);
    }

    // get parent fragment
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // check if parent Fragment implements listener
        if (getParentFragment() instanceof OnUpdatedTransactionDataListener) {
            parentListener = (OnUpdatedTransactionDataListener) getParentFragment();
        } else {
            throw new RuntimeException("The parent fragment must implement " +
                    "OnUpdatedTransactionDataListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        parentListener = null;
    }
}