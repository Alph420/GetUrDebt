package com.alphacorporations.givememymoney.ViewModel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.alphacorporations.givememymoney.model.MainViewModel;
import com.alphacorporations.givememymoney.model.repositories.DebtRepository;
import java.util.concurrent.Executor;

/**
 * Created by Alph4 le 05/05/2020.
 * Projet: SaveMyTrip
 **/
public class ViewModelFactory implements ViewModelProvider.Factory {
    private final DebtRepository itemDebtSource;
    private final Executor executor;

    public ViewModelFactory(DebtRepository itemDebtSource, Executor executor) {
        this.itemDebtSource = itemDebtSource;
        this.executor = executor;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(itemDebtSource, executor);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
