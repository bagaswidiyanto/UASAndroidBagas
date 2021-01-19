package com.example.uasbagaswidiyanto;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Drink {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "nama")
    private String nama;
    @ColumnInfo(name = "image")
    private String image;

    @Ignore
    public Drink(String nama, String image){
        this.nama = nama;
        this.image = image;
    }
    public Drink(int id, String nama, String image){
        this.id = id;
        this.nama = nama;
        this.image = image;
    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getNama() {
        return nama;
    }
    public String getImage() {
        return image;
    }
}
