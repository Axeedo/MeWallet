package com.axeedo.mewallet.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.axeedo.mewallet.Utils.Constants;

@Database(entities = {Transaction.class, Category.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TransactionDAO transactionDAO();
    public abstract CategoryDAO categoryDAO();

    private static AppDatabase INSTANCE;

    //private AppDatabase(){}

    public static AppDatabase getDbInstance (Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, Constants.DB_NAME)
                    .addMigrations(MIGRATION_1_2)
                    .build();
        }
        return INSTANCE;
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE `categories` (`uid` INTEGER NOT NULL, "
                    + "`category_name` TEXT, PRIMARY KEY(`uid`))");
        }
    };
}
