package com.example.uasbagaswidiyanto;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public abstract class DataDao {
    @Query("SELECT * FROM Drink")
    public abstract List<Drink> getAll();

    @Query("DELETE FROM Drink")
    public abstract void deleteAll();

    @Insert
    public abstract void insertAll(Drink drink);

    @Update
    public abstract void update(Drink drink);

    @Delete
    public abstract void delete(Drink drink);
}
