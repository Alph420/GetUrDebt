package com.alphacorporations.givememymoney.model.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.alphacorporations.givememymoney.model.Debt

/**
 * Created by Alph4 le 24/05/2020.
 * Projet: Give Me My Money
 */
@Dao
interface DebtDao {
    @Query("SELECT * FROM Debt WHERE id = :idDebt")
    fun getDebt(idDebt: Long): LiveData<Debt?>?

    @get:Query("SELECT * FROM Debt")
    val debts: LiveData<List<Debt?>?>?

    @Insert
    fun insertDebt(debt: Debt?): Long

    @Update
    fun updateDebt(debt: Debt?): Int

    @Query("DELETE FROM Debt WHERE id = :idDebt")
    fun deleteDebt(idDebt: Long): Int
}