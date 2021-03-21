package com.axeedo.mewallet.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.axeedo.mewallet.Database.Category;
import com.axeedo.mewallet.Database.Repository;
import com.axeedo.mewallet.Database.Transaction;

import java.util.List;

public class appViewModel extends AndroidViewModel {
    private Repository mRepository;
    private LiveData<List<Transaction>> mTransactions;
    private LiveData<List<Category>> mCategories;

    public appViewModel(@NonNull Application application) {
        super(application);
        mRepository = new Repository(application);
        mTransactions = mRepository.getAllTransactions();
        mCategories = mRepository.getAllCategories();
    }


    //Transactions

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


    //Categories

    public LiveData<List<Category>> getAllCategories() {
        return mCategories;
    }

    public void insert(Category category) { mRepository.insert(category); }

    /**
     * Returns Category at given position from ViewModel internal Category List
     * @param position
     * @return
     */
    public Category getCategory(int position) {
        Category category;
        try{
            category = getAllCategories().getValue().get(position);
        } catch (RuntimeException e){
            return null;
        }
        return category;
    }
}
