package com.axeedo.mewallet.Database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity (tableName = "categories")
public class Category {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int uid;

    @ColumnInfo(name = "category_name")
    private String name;

    //Todo implement image storage and category color
    /*@ColumnInfo(name= "image_path")
    private String imagePath;

    @ColumnInfo(name= "category_color")
    private int color;*/

    public Category(){}

    @Ignore
    public Category(String name){
        this.name = name;
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

    public void setName(String name) { this.name = name; }


}
