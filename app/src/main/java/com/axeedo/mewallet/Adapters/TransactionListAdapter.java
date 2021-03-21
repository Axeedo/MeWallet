package com.axeedo.mewallet.Adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.axeedo.mewallet.Database.Transaction;
import com.axeedo.mewallet.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;

public class TransactionListAdapter extends RecyclerView.Adapter<TransactionListAdapter.TransactionViewHolder> {

    private final LayoutInflater mInflater;
    // cache
    private List<Transaction> mTransactions;

    public TransactionListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NotNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.transaction_item_fragment, parent, false);
        return new TransactionViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NotNull final TransactionViewHolder holder, int position) {
        if(mTransactions != null) {
            Transaction current = mTransactions.get(position);
            holder.setTransactionItem(current);
        } else {
            // Data not ready yet
            Transaction tmp = new Transaction("Loading transactions ...", 0.0);
            holder.setTransactionItem(tmp);
        }
    }

    //updates cached transactions list
    public void setTransactions(List<Transaction> transactions){
        mTransactions = transactions;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        // Check if data is ready or not yet
        if(mTransactions != null)
            return mTransactions.size();
        else return 0;
    }

    class TransactionViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTransactionName;
        private final TextView mTransactionValue;

        public TransactionViewHolder(View view) {
            super(view);
            mTransactionName = (TextView) view.findViewById(R.id.transaction_item_name);
            mTransactionValue = (TextView) view.findViewById(R.id.transaction_item_value);
        }

        /**
         * Parses a {@link Transaction} object (item) and puts it in the view.
         * @param item
         */
        public void setTransactionItem(@NonNull Transaction item){
            mTransactionName.setText(item.getName());

            //Locale is here because Android lint is showing a warning.
            mTransactionValue.setText(String.format(Locale.US,"%.2f", item.getValue()));
        }

        @NotNull
        @Override
        public String toString() {
            return super.toString() + " '" + mTransactionValue.getText() + "'";
        }
    }
}