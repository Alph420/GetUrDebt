package com.alphacorporations.givememymoney.model.repositories;

import androidx.lifecycle.LiveData;

import com.alphacorporations.givememymoney.model.Debt;
import com.alphacorporations.givememymoney.model.database.dao.DebtDao;

import java.util.List;

/**
 * Created by Alph4 le 24/05/2020.
 * Projet: Give Me My Money
 **/
public class DebtRepository {

    private final DebtDao mDebtDao;

    public DebtRepository(DebtDao debtDao) { mDebtDao = debtDao; }

    //--- GET ---
    public LiveData getDebt(long debtId) {
        return this.mDebtDao.getDebt(debtId);
    }

    public LiveData<List<Debt>> getDebts() {
        return this.mDebtDao.getDebts();
    }

    //--- INSERT ---
    public void insertDebt(Debt debt) {
        this.mDebtDao.insertDebt(debt);
    }

    //--- UPDATE ---
    public void updateDebt(Debt debt) {
        this.mDebtDao.updateDebt(debt);
    }

    //--- DELETE ---
    public void deleteDebt(long idDebt) {
        this.mDebtDao.deleteDebt(idDebt);
    }
}
