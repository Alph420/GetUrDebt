package com.alphacorporations.givememymoney.View

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alphacorporations.givememymoney.Constant.DEBT_ID
import com.alphacorporations.givememymoney.Constant.FIREBASE_COLLECTION_ID
import com.alphacorporations.givememymoney.Constant.SELECT_PICTURE
import com.alphacorporations.givememymoney.R
import com.alphacorporations.givememymoney.View.listActivity.ListDebtActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_add_debt.*
import java.text.SimpleDateFormat
import java.util.*

class AddDebtActivity : AppCompatActivity() {

    //GOBAL VAR
    var mStorageRef: StorageReference = FirebaseStorage.getInstance().reference
    private val db = Firebase.firestore
    private var avatarUri: String? = null
    private var date: String? = null
    private var calendar: Calendar = Calendar.getInstance()
    var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_debt)
        adsConfig()

        avatar!!.setOnClickListener { selectAvatar() }
        first_name_debt!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                if (s.isNotEmpty()) {
                    save_debt!!.isEnabled = true
                    save_debt!!.setTextColor(Color.WHITE)
                }
                if (s.isEmpty()) {
                    save_debt!!.isEnabled = false
                    save_debt!!.setTextColor(Color.RED)
                }
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.isNotEmpty()) {
                    save_debt!!.isEnabled = true
                    save_debt!!.setTextColor(Color.WHITE)
                }
                if (s.isEmpty()) {
                    save_debt!!.isEnabled = false
                    save_debt!!.setTextColor(Color.RED)
                }
            }

            override fun afterTextChanged(s: Editable) {
                if (s.isNotEmpty()) {
                    save_debt!!.isEnabled = true
                    save_debt!!.setTextColor(Color.WHITE)
                }
                if (s.isEmpty()) {
                    save_debt!!.isEnabled = false
                    save_debt!!.setTextColor(Color.RED)
                }
            }
        })
        save_debt!!.setOnClickListener { saving() }
    }


    private fun selectAvatar() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        intent.action = Intent.ACTION_OPEN_DOCUMENT
        startActivityForResult(Intent.createChooser(intent, ""), SELECT_PICTURE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            imageUri = data!!.data
            avatarUri = imageUri.toString()
            avatar!!.setImageURI(imageUri)
        }
    }

    private fun date() {
        calendar.get(Calendar.DAY_OF_MONTH)
        calendar.get(Calendar.MONTH)
        calendar.get(Calendar.YEAR)
        date = SimpleDateFormat("dd/MM/yyyy").format(calendar.time)
    }

    private fun saving() {
        date()

        val data = hashMapOf(
                "img" to avatarUri,
                "name" to first_name_debt!!.text.toString() + " " + last_name_debt!!.text,
                "reason" to if (reason_debt.toString() == "") null else reason_debt!!.text.toString(),
                "date" to date,
                "amount" to if (amount_debt!!.text.toString() == "") 0 else amount_debt!!.text.toString().toLong(),
                "isDebt" to true
        )

        db.collection(FIREBASE_COLLECTION_ID)
                .add(data)
                .addOnSuccessListener { documentReference ->
                    Log.e(Context.ACTIVITY_SERVICE, "DocumentSnapshot added with ID: ${documentReference.id}")
                    saveImgOnFirebaseStorage(documentReference.id)
                    startActivity(Intent(this, ListDebtActivity::class.java))
                    finish()
                }
                .addOnFailureListener { e ->
                    Log.w(Context.ACTIVITY_SERVICE, "Error adding document", e)
                    Toast.makeText(this, "Erreur dans l'enregistrement de la dette", Toast.LENGTH_LONG).show()
                }

    }

    private fun saveImgOnFirebaseStorage(id: String) {
        val ref: StorageReference = mStorageRef.child("debt_images/$id")
        imageUri?.let { ref.putFile(it) }
    }


    private fun adsConfig() {
        MobileAds.initialize(this) { }
        val adRequest: AdRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }
}