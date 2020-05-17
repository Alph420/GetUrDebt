package com.alphacorporations.givememymoney.events;

import com.alphacorporations.givememymoney.model.Debt;

/**
 * Created by Alph4 le 17/05/2020.
 * Projet: Give Me My Money
 **/
public class DeleteDebtEvent {
    public Debt debt;

    public DeleteDebtEvent(Debt debt) {
        this.debt = debt;
    }
}
