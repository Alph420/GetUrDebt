package com.alphacorporations.givememymoney.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alphacorporations.givememymoney.model.Debt
import com.alphacorporations.givememymoney.model.database.dao.DebtDao

/**
 * Created by Alph4 le 24/05/2020.
 * Projet: Give Me My Money
 */
@Database(entities = [Debt::class], version = 1, exportSchema = false)
abstract class DebtDatabase : RoomDatabase() {
    abstract fun debtDao(): DebtDao?

    companion object {
        @Volatile
        private var INSTANCE: DebtDatabase? = null

        // --- INSTANCE ---
        @JvmStatic
        fun getInstance(context: Context?): DebtDatabase? {
            if (INSTANCE == null) {
                synchronized(DebtDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context!!,
                                DebtDatabase::class.java, "Debt.db")
                                .build()
                    }
                }
            }
            return INSTANCE
        }
    }
}