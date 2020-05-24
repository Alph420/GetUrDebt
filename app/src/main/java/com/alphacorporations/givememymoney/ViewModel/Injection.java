package com.alphacorporations.givememymoney.ViewModel;

import android.content.Context;

import com.alphacorporations.givememymoney.model.database.DebtDatabase;
import com.alphacorporations.givememymoney.model.repositories.DebtRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Alph4 le 24/05/2020.
 * Projet: Give Me My Money
 **/
public class Injection {

    public static DebtRepository provideDebtDataSource(Context context) {
        DebtDatabase database = DebtDatabase.getInstance(context);
        return new DebtRepository(database.debtDao());
    }

    public static Executor provideExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        DebtRepository dataSourceItem = provideDebtDataSource(context);
        Executor executor = provideExecutor();
        return new ViewModelFactory(dataSourceItem, executor);
    }
}
