package com.alphacorporations.givememymoney.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.alphacorporations.givememymoney.model.repositories.DebtRepository;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * Created by Alph4 le 18/05/2020.
 * Projet: Todoc
 **/
public class MainViewModel extends ViewModel {

    // REPOSITORIES
    private final DebtRepository debtDataSource;
    private final Executor executor;


    public MainViewModel(DebtRepository taskDataSource, Executor executor) {
        this.debtDataSource = taskDataSource;
        this.executor = executor;
    }

    // FOR TASK
    public LiveData getDebtsById(long debtId) {
        return debtDataSource.getDebt(debtId);
    }

    public LiveData<List<Debt>> getDebts() {
        return debtDataSource.getDebts();
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
