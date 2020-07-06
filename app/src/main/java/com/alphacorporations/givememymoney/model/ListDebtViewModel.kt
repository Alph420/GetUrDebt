package com.alphacorporations.givememymoney.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.alphacorporations.givememymoney.model.repositories.DebtRepository
import java.util.concurrent.Executor

/**
 * Created by Alph4 le 18/05/2020.
 * Projet: Todoc
 */
class ListDebtViewModel(
        // REPOSITORIES
        private val debtDataSource: DebtRepository,
        private val executor: Executor) : ViewModel() {

    // FOR TASK
    val debts: LiveData<List<Debt?>?>?
        get() = debtDataSource.debts

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