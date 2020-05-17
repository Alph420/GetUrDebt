package com.alphacorporations.givememymoney.service;

import com.alphacorporations.givememymoney.model.Debt;

import java.util.List;

/**
 * Created by Alph4 le 17/05/2020.
 * Projet: Give Me My Money
 **/
public class DummyDebtApiService implements DebtApiService {

    private List<Debt> mDebts = DummyDebtGenerator.generateDebt();


    @Override
    public List<Debt> getDetbs() { return mDebts;}

    @Override
    public void deleteDebt(Debt debt) {
        mDebts.remove(debt);
    }

    @Override
    public void createDebt(Debt debt) {
        mDebts.add(debt);
    }
}
