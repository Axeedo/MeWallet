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
public interface CategoryDAO {
    // Returns the number of rows affected
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public ListenableFuture<List<Long>> insertAll (Category ... categories);

    @Delete
    public ListenableFuture<Integer> deleteCategories(List<Category> categories);

    @Query("SELECT * FROM categories")
    public LiveData<List<Category>> getAll();

    @Query("SELECT * FROM categories WHERE uid = :categories_id")
    public LiveData<List<Category>> getById(int categories_id);

    @Update
    public ListenableFuture<Integer> updateCategories(List<Category> categories);
}
