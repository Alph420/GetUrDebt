package com.alphacorporations.givememymoney.View

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.alphacorporations.givememymoney.R
import com.alphacorporations.givememymoney.model.Debt
import kotlinx.android.synthetic.main.activity_profile_debt.*

class ProfileDebtActivity : AppCompatActivity() {

    private var debt: Debt? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_debt)

        avatar!!.setOnClickListener { selectAvatar() }
        save_debt!!.setOnClickListener { save() }
    }

    /*
    private fun initDebtProfile() {
        if (debt!!.img == null) avatar!!.setImageResource(R.drawable.ic_person_green) else avatar!!.setImageURI(Uri.parse(debt!!.img))
        name_debt!!.setText(debt!!.name)
        object_debt!!.setText(debt!!.reason)
        amount_debt!!.setText(debt!!.amount.toString())
    }

    private fun populateList() {
        val debtObserver = Observer { debtList: Debt? ->
            if (debtList != null) {
                debt = debtList
                initDebtProfile()
            }
        }
       // mProfileDebtViewModel!!.getDebtsById(Constant.idDebt)!!.observe(this, debtObserver)
    }
 */

    fun selectAvatar() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, ""), SELECT_PICTURE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val imageUri = data!!.data
            avatar!!.setImageURI(imageUri)
            debt!!.img = imageUri.toString()
        }
    }

    private fun save() {
        debt!!.name = name_debt!!.text.toString()
        debt!!.reason = object_debt!!.text.toString()
        val amountstring = amount_debt!!.text.toString()
        val amount = amountstring.toLong()
        debt!!.amount = amount
        finish()
    }

    companion object {
        const val SELECT_PICTURE = 1
    }
}