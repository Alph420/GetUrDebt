package com.alphacorporations.givememymoney.event

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.alphacorporations.givememymoney.Constant.DEBT_ID
import com.alphacorporations.givememymoney.View.profilesActivity.ProfileDebtActivity


class OpenDebtEvent{
    fun OpenDebtEvent(v: View) {
        val intent = Intent(v.context, ProfileDebtActivity::class.java)
        val bundle = Bundle()
        bundle.putString("idDebt", DEBT_ID)
        intent.putExtras(bundle)
        v.context.startActivity(intent)
    }
}