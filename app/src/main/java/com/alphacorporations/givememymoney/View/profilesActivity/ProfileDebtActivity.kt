package com.alphacorporations.givememymoney.View.profilesActivity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alphacorporations.givememymoney.Constant.SELECT_PICTURE
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
        val amount = amount_debt!!.text.toString().toLong()
        debt!!.amount = amount
        finish()
    }
}