package com.alphacorporations.givememymoney.event

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.alphacorporations.givememymoney.Constant
import com.alphacorporations.givememymoney.View.ProfileDebtActivity

class OpenDebtEvent(v: View) {
    init {
        val intent = Intent(v.context, ProfileDebtActivity::class.java)
        val bundle = Bundle()
        bundle.putLong("DebtId", Constant.idDebt)
        intent.putExtras(bundle)
        v.context.startActivity(intent)
    }
}