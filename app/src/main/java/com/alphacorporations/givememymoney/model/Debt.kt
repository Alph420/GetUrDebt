package com.alphacorporations.givememymoney.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Alph4 le 10/05/2020.
 * Projet: Give Me My Money
 */
@Entity
class Debt(@field:PrimaryKey(autoGenerate = true)
           var id: Long,
           var img: String?,
           var name: String,
           var `object`: String?,
           var date: String?,
           var amount: Int)