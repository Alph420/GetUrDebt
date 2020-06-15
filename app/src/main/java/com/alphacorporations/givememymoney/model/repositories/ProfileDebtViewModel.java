package com.alphacorporations.givememymoney.model.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.alphacorporations.givememymoney.model.Debt;
import java.util.concurrent.Executor;

/**
 * Created by Alph4 le 12/06/2020.
 * Projet: Give Me My Money
 **/
public class ProfileDebtViewModel extends ViewModel {

    // REPOSITORIES
    private final DebtRepository debtDataSource;
    private final Executor executor;


    public ProfileDebtViewModel(DebtRepository taskDataSource, Executor executor) {
        this.debtDataSource = taskDataSource;
        this.executor = executor;
    }

    // FOR TASK
    public LiveData<Debt> getDebtsById(long debtId) {
        return debtDataSource.getDebt(debtId);
    }


    public void createDebt(final Debt task) {
        executor.execute(() -> debtDataSource.insertDebt(task));
    }

    public void deleteDebt(long debtId) {
        executor.execute(() -> debtDataSource.deleteDebt(debtId));
    }

    public void updateDebt(Debt task) {
        executor.execute(() -> debtDataSource.updateDebt(task));
    }

}
