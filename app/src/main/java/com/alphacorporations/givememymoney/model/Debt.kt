package com.alphacorporations.givememymoney.model


/**
 * Created by Alph4 le 10/05/2020.
 * Projet: Give Me My Money
 */
class Debt {

    companion object Factory {
        fun create(): Debt = Debt()
    }



    var id: String? = null
    var img: String? = null
    var name: String? = null
    var reason: String? = null
    var date: String? = null
    var amount: Long? = null


}
