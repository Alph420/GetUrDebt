package com.alphacorporations.givememymoney.model.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.alphacorporations.givememymoney.model.Debt
import java.util.concurrent.Executor

/**
 * Created by Alph4 le 12/06/2020.
 * Projet: Give Me My Money
 */
class ProfileDebtViewModel(// REPOSITORIES
        private val debtDataSource: DebtRepository, private val executor: Executor) : ViewModel() {

    // FOR TASK
    fun getDebtsById(debtId: Long): LiveData<Debt?>? {
        return debtDataSource.getDebt(debtId)
    }

    fun createDebt(task: Debt?) {
        executor.execute { debtDataSource.insertDebt(task) }
    }

    fun deleteDebt(debtId: Long) {
        executor.execute { debtDataSource.deleteDebt(debtId) }
    }

    fun updateDebt(task: Debt?) {
        executor.execute { debtDataSource.updateDebt(task) }
    }

}