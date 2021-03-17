package com.axeedo.mewallet.Database;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity (tableName = "transactions")
public class Transaction {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int uid;

    @ColumnInfo(name = "transaction_name")
    private String name;

    @ColumnInfo(name = "transaction_value")
    private Double value; // Converted into DecimalFormat when displayed

    // TODO implement category
    // Foreign key
    // private int category;

    public Transaction() { }

    @Ignore
    public Transaction(String name, Double value) {
        this.name = name;
        this.value = value;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}