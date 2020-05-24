package com.alphacorporations.givememymoney.model.database;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.alphacorporations.givememymoney.model.Debt;
import com.alphacorporations.givememymoney.model.database.dao.DebtDao;

import java.util.Calendar;

/**
 * Created by Alph4 le 24/05/2020.
 * Projet: Give Me My Money
 **/
@Database(entities = {Debt.class}, version = 1, exportSchema = false)
public abstract class DebtDatabase extends RoomDatabase {

    private static volatile DebtDatabase INSTANCE;

    public abstract DebtDao debtDao();

    // --- INSTANCE ---
    public static DebtDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (DebtDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context,
                            DebtDatabase.class, "Debt.db")
                            .addCallback(prepopulateDatabase())
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static Callback prepopulateDatabase() {
        return new Callback() {

            Calendar calendar = Calendar.getInstance();

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                ContentValues DebtTest = new ContentValues();
                DebtTest.put("id", 1);
                DebtTest.put("img", "");
                DebtTest.put("name", "Debt de test");
                DebtTest.put("object", "Test de prepolation");
                DebtTest.put("date", "24/05/2020");
                DebtTest.put("amount", 50);


                ContentValues DebtTestSecondary = new ContentValues();
                DebtTestSecondary.put("id", 2);
                DebtTestSecondary.put("img", "");
                DebtTestSecondary.put("name", "Debt de test 2 ");
                DebtTestSecondary.put("object", "Test de prepolation 2 ");
                DebtTestSecondary.put("date", "15/05/2020");
                DebtTestSecondary.put("amount", 500);

                db.insert("Debt", OnConflictStrategy.IGNORE, DebtTest);
                db.insert("Debt", OnConflictStrategy.IGNORE, DebtTestSecondary);
            }
        };
    }
}
