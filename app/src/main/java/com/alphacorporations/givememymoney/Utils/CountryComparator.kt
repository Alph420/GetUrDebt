package com.alphacorporations.givememymoney.Utils

import com.alphacorporations.givememymoney.model.Country
import java.text.Collator


/**
Created by Alph4 le 09/08/2020.
Projet: Give Me My Money
CountryComparator class.
 **/
class CountryComparator internal constructor() : Comparator<Country?> {
    private val comparator: Comparator<Any?>
    override fun compare(o1: Country?, o2: Country?): Int {
        return comparator.compare(o1!!.name, o2!!.name)
    }

    init {
        comparator = Collator.getInstance()
    }
}