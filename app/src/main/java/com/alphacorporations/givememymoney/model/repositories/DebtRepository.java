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
    public Debt getDebt(long projectId) {
        return this.mDebtDao.getDebt(projectId);
    }

    public LiveData<List<Debt>> getDebts() {
        return this.mDebtDao.getDebts();
    }

    //--- INSERT ---
    public void insertDebt(Debt project) {
        this.mDebtDao.insertDebt(project);
    }

    //--- UPDATE ---
    public void updateDebt(Debt project) {
        this.mDebtDao.updateDebt(project);
    }

    //--- DELETE ---
    public void deleteDebt(long idProject) {
        this.mDebtDao.deleteDebt(idProject);
    }
}
