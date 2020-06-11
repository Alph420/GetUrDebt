package com.alphacorporations.givememymoney.model.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.alphacorporations.givememymoney.model.Debt;

import java.util.List;

/**
 * Created by Alph4 le 24/05/2020.
 * Projet: Give Me My Money
 **/
@Dao
public interface DebtDao {

    @Query("SELECT * FROM Debt WHERE id = :idDebt")
    LiveData<Debt> getDebt(long idDebt);

    @Query("SELECT * FROM Debt")
    LiveData<List<Debt>> getDebts();

    @Insert
    long insertDebt(Debt debt);

    @Update
    int updateDebt(Debt debt);

    @Query("DELETE FROM Debt WHERE id = :idDebt")
    int deleteDebt(long idDebt);
}
