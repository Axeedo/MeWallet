package com.axeedo.mewallet.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TransactionDAO {

    @Insert
    void insertAll (Transaction ... transactions);

    @Delete
    void deleteTransaction(Transaction transaction);

    @Query("SELECT * FROM transactions")
    List<Transaction> getAll();

    @Update
    void update(Transaction transaction);
}
