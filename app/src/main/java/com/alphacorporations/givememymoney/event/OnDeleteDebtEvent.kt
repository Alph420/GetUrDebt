package com.alphacorporations.givememymoney.event

import com.alphacorporations.givememymoney.model.Debt

/**
Created by Alph4 le 15/07/2020.
Projet: Give Me My Money
 **/
class DeleteMeetingEvent(debt: Debt) {
    /**
     * Debt to delete
     */
    var debt: Debt = debt

}
