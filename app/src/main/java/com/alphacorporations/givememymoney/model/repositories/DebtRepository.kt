package com.alphacorporations.givememymoney.model.repositories

import androidx.lifecycle.LiveData
import com.alphacorporations.givememymoney.model.Debt
import com.alphacorporations.givememymoney.model.database.dao.DebtDao

/**
 * Created by Alph4 le 24/05/2020.
 * Projet: Give Me My Money
 */
class DebtRepository(private val mDebtDao: DebtDao) {

    //--- GET ---
    fun getDebt(debtId: Long): LiveData<Debt?>? {
        return mDebtDao.getDebt(debtId)
    }

    val debts: LiveData<List<Debt?>?>?
        get() = mDebtDao.debts

    //--- INSERT ---
    fun insertDebt(debt: Debt?) {
        mDebtDao.insertDebt(debt)
    }

    //--- UPDATE ---
    fun updateDebt(debt: Debt?) {
        mDebtDao.updateDebt(debt)
    }

    //--- DELETE ---
    fun deleteDebt(idDebt: Long) {
        mDebtDao.deleteDebt(idDebt)
    }

}