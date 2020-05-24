package com.alphacorporations.givememymoney.model;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Alph4 le 10/05/2020.
 * Projet: Give Me My Money
 **/
@Entity
public class Debt {

    @PrimaryKey(autoGenerate = true)
    private long id;
    @Nullable
    private int img;
    private String name;
    @Nullable
    private String object;
    @Nullable
    private String date;
    private int amount;

    public Debt(long id, int img, String name, String object, String date, int amount) {
        this.id = id;
        this.img = img;
        this.name = name;
        this.object = object;
        this.date = date;
        this.amount = amount;
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id;}

    public int getImg() {
        return img;
    }

    public void setImg(int img) { this.img = img;}

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getObject() { return object; }

    public void setObject(String object) { this.object = object; }

    public String getDate() {
        return date;
    }

    public void setDate(String date) { this.date = date; }

    public int getAmount() { return amount; }

    public void setAmount(int amount) { this.amount = amount;}
}


