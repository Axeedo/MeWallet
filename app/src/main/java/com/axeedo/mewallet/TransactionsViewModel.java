package com.axeedo.mewallet;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.axeedo.mewallet.Database.Repository;
import com.axeedo.mewallet.Database.Transaction;

import java.util.List;

public class TransactionsViewModel extends AndroidViewModel {
    private Repository mRepository;
    private LiveData<List<Transaction>> mTransactions;

    public TransactionsViewModel(@NonNull Application application) {
        super(application);
        mRepository = new Repository(application);
        mTransactions = mRepository.getAllTransactions();
    }

    public LiveData<List<Transaction>> getAllTransactions() { return mTransactions; }

    public void insert(Transaction transaction) { mRepository.insert(transaction); }

    /**
     * Returns Transaction at given position from ViewModel internal Transaction List
     * @param position
     * @return
     */
    public Transaction getTransaction(int position) {
        Transaction transaction;
        try{
            transaction = getAllTransactions().getValue().get(position);
        } catch (RuntimeException e){
            return null;
        }
        return transaction;
    }
}
