package com.axeedo.mewallet.TransactionFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.axeedo.mewallet.Database.Transaction;
import com.axeedo.mewallet.R;
import com.axeedo.mewallet.TransactionsViewModel;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TransactionDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransactionDetailFragment extends Fragment {

    private static final String ARG_POSITION = "selected_transaction_position";
    private int mPosition;
    private Transaction mTransaction;

    public TransactionDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TransactionOverviewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TransactionDetailFragment newInstance(String param1, String param2) {
        TransactionDetailFragment fragment = new TransactionDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_POSITION, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPosition = getArguments().getInt(ARG_POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.transaction_detail_fragment, container, false);
        TextView name = view.findViewById(R.id.transaction_name);
        TextView value = view.findViewById(R.id.transaction_value);

        TransactionsViewModel transactionsViewModel = new ViewModelProvider(requireActivity())
                .get(TransactionsViewModel.class);
        mTransaction = transactionsViewModel.getAllTransactions().getValue().get(mPosition);
        if (mTransaction == null){
            mTransaction = new Transaction("No transaction found", 0.0);
        }

        name.setText(mTransaction.getName());
        value.setText(String.format(Locale.US,"%.2f", mTransaction.getValue()));

        return view;
    }
}