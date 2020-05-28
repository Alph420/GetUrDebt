package com.alphacorporations.givememymoney.model.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.alphacorporations.givememymoney.model.Debt;
import com.alphacorporations.givememymoney.model.database.dao.DebtDao;

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
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
