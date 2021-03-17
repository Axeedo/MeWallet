package com.axeedo.mewallet.TransactionFragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.axeedo.mewallet.Database.Transaction;
import com.axeedo.mewallet.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TransactionEditorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransactionEditorFragment extends Fragment {

    OnUpdatedTransactionDataListener parentListener;

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
        TransactionEditorFragment fragment = new TransactionEditorFragment();
        return fragment;
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

        Button saveBtn = getView().findViewById(R.id.save_transaction_button);
        saveBtn.setOnClickListener( (View v)->{
            Transaction newTransaction = getTransactionFromView(view);

            // Let Parent fragment (or any class implementing OnUpdatedTransactionDataListener)
            // decide what to do with the new Transaction object
            parentListener.newTransactionNotification(newTransaction);
        });
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
        return new Transaction(name, value);
    }

    /**
     * This interface is used to notify the parent of a new or an updated transaction
     */
    public interface OnUpdatedTransactionDataListener {
        void newTransactionNotification(Transaction newTransaction);
    }

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