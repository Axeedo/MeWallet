package com.axeedo.mewallet;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.axeedo.mewallet.Database.Transaction;
import com.axeedo.mewallet.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class transactionListAdapter extends RecyclerView.Adapter<transactionListAdapter.ViewHolder> {

    private final List<Transaction> mValues;

    public transactionListAdapter(List<Transaction> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transaction_item_fragment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mTransactionName.setText(mValues.get(position).getName());
        holder.mTransactionValue.setText(String.format("%.2f", mValues.get(position).getValue()));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTransactionName;
        public final TextView mTransactionValue;
        public Transaction mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTransactionName = (TextView) view.findViewById(R.id.transaction_item_name);
            mTransactionValue = (TextView) view.findViewById(R.id.transaction_item_value);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTransactionValue.getText() + "'";
        }
    }
}