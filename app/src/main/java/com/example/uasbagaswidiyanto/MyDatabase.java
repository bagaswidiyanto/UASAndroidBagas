package com.example.uasbagaswidiyanto;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Drink.class}, version = 1, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {
    public abstract DataDao dataDao();
}


