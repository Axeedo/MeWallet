package com.axeedo.mewallet.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

@Dao
public interface TransactionDAO {

    // Returns the number of rows affected
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public ListenableFuture<List<Long>> insertAll (Transaction ... transactions);

    @Delete
    public ListenableFuture<Integer> deleteTransactions(List<Transaction> transactions);

    @Query("SELECT * FROM transactions")
    public LiveData<List<Transaction>> getAll();

    @Query("SELECT * FROM transactions WHERE uid = :transaction_id")
    public LiveData<List<Transaction>> getById(int transaction_id);

    @Update
    public ListenableFuture<Integer> updateTransactions(List<Transaction> transactions);
}
