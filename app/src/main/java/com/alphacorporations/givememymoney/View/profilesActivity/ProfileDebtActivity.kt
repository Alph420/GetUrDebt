package com.alphacorporations.givememymoney.View.profilesActivity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alphacorporations.givememymoney.Constant.DEBT_ID
import com.alphacorporations.givememymoney.Constant.FIREBASE_COLLECTION_ID
import com.alphacorporations.givememymoney.Constant.SELECT_PICTURE
import com.alphacorporations.givememymoney.R
import com.alphacorporations.givememymoney.model.Debt
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_profile_debt.*
import kotlinx.android.synthetic.main.activity_profile_debt.amount_debt
import kotlinx.android.synthetic.main.activity_profile_debt.avatar
import kotlinx.android.synthetic.main.activity_profile_debt.reason_debt
import kotlinx.android.synthetic.main.activity_profile_debt.save_debt


class ProfileDebtActivity : AppCompatActivity() {

    private var db = Firebase.firestore
    private var debt:Debt? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_debt)

         db = FirebaseFirestore.getInstance()

        getDebtFromFirebase()
        initDebtProfile()

        avatar!!.setOnClickListener { selectAvatar() }
        save_debt!!.setOnClickListener { save() }
    }

    fun getDebtFromFirebase(){
        db.collection(FIREBASE_COLLECTION_ID).document(DEBT_ID).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        debt?.img = document.data?.get("img").toString()
                        debt?.name = document.data?.get("name").toString()
                        debt?.reason = document.data?.get("reason").toString()
                        debt?.date =  document.data?.get("date").toString()
                        debt?.amount = document.data?.get("amount").toString().toLong()
                    }
                    else
                    {
                        //TODO ERROR MSG
                    }
                }
    }

    fun initDebtProfile(){
        Glide.with(this).load(debt?.img).into(avatar)
        name_debt.setText(debt?.name)
        amount_debt.setText(debt?.amount.toString().plus('€'))
        reason_debt.setText(debt?.reason)
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
        debt!!.reason = reason_debt!!.text.toString()
        val amount = amount_debt!!.text.toString().toLong()
        debt!!.amount = amount
        finish()
    }
}