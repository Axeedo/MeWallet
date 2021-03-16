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

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    OnUpdatedTransactionData parentListener;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            /*mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);*/
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

        Button saveBtn = getView().findViewById(R.id.save_transaction_button);
        saveBtn.setOnClickListener( (View v)->{
            EditText nameInput = getView().findViewById(R.id.new_transaction_name);
            EditText valueInput = getView().findViewById(R.id.new_transaction_value);
            //Todo handle category selection

            String name = nameInput.getText().toString();
            double value = 0.0;
            try{
                value = Double.parseDouble(valueInput.getText().toString());
            }catch (NumberFormatException e) {
                //TODO refuse save
            }

            /* Let parent decide what to do with the new Transaction object */
            parentListener.newTransactionNotification(new Transaction(name, value));
        });
    }

    /**
     * This interface is used to notify the parent of a new or an updated transaction
     */
    public interface OnUpdatedTransactionData{
        void newTransactionNotification(Transaction newTransaction);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // check if parent Fragment implements listener
        if (getParentFragment() instanceof OnUpdatedTransactionData) {
            parentListener = (OnUpdatedTransactionData) getParentFragment();
        } else {
            throw new RuntimeException("The parent fragment must implement OnChildFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        parentListener = null;
    }
}