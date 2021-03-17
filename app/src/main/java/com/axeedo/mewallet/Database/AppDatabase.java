package com.axeedo.mewallet.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Transaction.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TransactionDAO transactionDAO();

    private static AppDatabase INSTANCE;

    //private AppDatabase(){}

    public static AppDatabase getDbInstance (Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "DB_MeWallet")
                    .build();
        }
        return INSTANCE;
    }
}
