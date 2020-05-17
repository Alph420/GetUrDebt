package com.alphacorporations.givememymoney.model;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Alph4 le 10/05/2020.
 * Projet: Give Me My Money
 **/
public class Debt {
    private int img;
    private String name;
    private String object;
    private String date;
    private int amount;

    public Debt(int img, String name, String object, String date, int amount) {
        this.img = img;
        this.name = name;
        this.object = object;
        this.date = date;
        this.amount = amount;
    }

    public int getImg() { return img; }

    public void setImg(int img) { this.img = img;}

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getObject() { return object; }

    public void setObject(String object) { this.object = object; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public int getAmount() { return amount; }

    public void setAmount(int amount) { this.amount = amount;}
}
