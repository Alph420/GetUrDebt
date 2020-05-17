package com.alphacorporations.givememymoney.service;


import com.alphacorporations.givememymoney.model.Debt;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import static android.graphics.Color.rgb;

/**
 * Created by Alph4 le 17/05/2020.
 * Projet: Give Me My Money
 **/
public class DummyDebtGenerator {

    private static int actualColor;

    public static int getActualColor() {
        return actualColor;
    }

    private static Calendar date = Calendar.getInstance();
    private static SimpleDateFormat sDateFormat = new SimpleDateFormat("dd/MM/yy");
    private static String dateFormated = sDateFormat.format(date.getTime());


    private static List<Debt> DUMMY_DEBT = Arrays.asList(
            new Debt(generateColor(), "Philippe lapelle", "Planche en bois", dateFormated, 50),
            new Debt(generateColor(), "Raphael Dugrenier", "Clous", dateFormated, 10),
            new Debt(generateColor(), "ALLON LEVY", "Meubles", dateFormated, 150),
            new Debt(generateColor(), "CHATZIDZAKIS ZOE", "Boites", dateFormated, 20),
            new Debt(generateColor(), "DELLO STRITTO PIETRO", "", dateFormated, 10));


    static List<Debt> generateDebt() {
        return new ArrayList<>(DUMMY_DEBT);
    }

    public static int generateColor() {
        actualColor = rgb(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255));
        return actualColor;
    }
}
