package com.axeedo.mewallet.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class Repository {
    private TransactionDAO mTransactionDao;
    private CategoryDAO mCategoryDao;
    private LiveData<List<Transaction>> mAllTransactions;
    private LiveData<List<Category>> mAllCategories;

    public Repository(Application application){
        AppDatabase db = AppDatabase.getDbInstance(application);
        // DAOs
        mTransactionDao = db.transactionDAO();
        mCategoryDao = db.categoryDAO();
        // LiveDatas
        mAllTransactions = mTransactionDao.getAll();
        mAllCategories = mCategoryDao.getAll();
    }

    public LiveData<List<Transaction>> getAllTransactions(){
        return mAllTransactions;
    }
    public LiveData<List<Category>> getAllCategories(){
        return mAllCategories;
    }

    public void insert (Transaction transaction) {
        mTransactionDao.insertAll(transaction);
    }
    public void insert (Category category) {
        mCategoryDao.insertAll(category);
    }
}
