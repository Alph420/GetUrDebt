package com.alphacorporations.givememymoney.service;

import com.alphacorporations.givememymoney.model.Debt;

import java.util.List;

/**
 * Created by Alph4 le 10/05/2020.
 * Projet: Give Me My Money
 **/
public interface DebtApiService {

    List<Debt> getDetbs();

    void deleteDebt(Debt debt);

    void createDebt(Debt debt);

}
