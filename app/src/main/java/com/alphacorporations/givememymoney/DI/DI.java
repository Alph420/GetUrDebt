package com.alphacorporations.givememymoney.DI;

import com.alphacorporations.givememymoney.service.DummyDebtApiService;
import com.alphacorporations.givememymoney.service.DebtApiService;

/**
 * Created by Alph4 le 10/05/2020.
 * Projet: Give Me My Money
 **/
public class DI {
    private static DebtApiService service = new DummyDebtApiService();

    /**
     * Get an instance on @{@link DebtApiService}
     *
     * @return
     */
    public static DebtApiService getDebtApiService() {
        return service;
    }

    /**
     * Get always a new instance on @{@link DebtApiService}. Useful for tests, so we ensure the context is clean.
     *
     * @return
     */
    public static DebtApiService getNewInstanceApiService() {
        return new DummyDebtApiService();
    }
}
