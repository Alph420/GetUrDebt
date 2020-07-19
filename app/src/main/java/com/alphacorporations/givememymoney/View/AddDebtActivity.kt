package com.alphacorporations.givememymoney.View

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.nfc.Tag
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.alphacorporations.givememymoney.Constant.FIREBASE_ITEM
import com.alphacorporations.givememymoney.R
import com.alphacorporations.givememymoney.model.Debt
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_add_debt.*

class AddDebtActivity : AppCompatActivity() {

    //PRIVATE VAR
    val db = Firebase.firestore
    private var avatarUri: String? = null
    private var date: String? = null
    private var picker: DatePicker? = null

    //ANOTHER VAR
    var imageUri: Uri? = null
    var setDate = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_debt)

        avatar!!.setOnClickListener { selectAvatar() }

        switch_date!!.setOnCheckedChangeListener { _: CompoundButton?, _: Boolean ->
            if (switch_date!!.isChecked) {
                println("VISIBLE")
                date_debt!!.visibility = View.VISIBLE
            } else {
                date_debt!!.visibility = View.INVISIBLE
            }
        }
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
        date_debt!!.setOnClickListener { date() }
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
        val builderDatePicker = AlertDialog.Builder(this)
        picker = DatePicker(this)
        picker!!.calendarViewShown = false
        builderDatePicker.setView(picker)
        builderDatePicker.setPositiveButton("OK") { _: DialogInterface?, _: Int ->
            val year = picker!!.year
            val mon = picker!!.month
            val day = picker!!.dayOfMonth
            date = "$day/$mon/$year"
            setDate = true
        }
        builderDatePicker.setNegativeButton(
                "Annuler"
        ) { dialog: DialogInterface, _: Int -> dialog.cancel() }
        builderDatePicker.show()
    }

    private fun saving() {

        //TODO ANIMATION FULL SCREEN SAVING -PROGRESS DIALOG-

        val data = hashMapOf(
                "img" to avatarUri,
                "name" to first_name_debt!!.text.toString() + " " + last_name_debt!!.text,
                "reason" to if (object_debt.toString() == "") null else object_debt!!.text.toString(),
                "date" to date,
                "amount" to if (amount_debt!!.text.toString() == "") 0 else amount_debt!!.text.toString().toLong()
        )

        db.collection(FIREBASE_ITEM)
                .add(data)
                .addOnSuccessListener { documentReference ->
                    Log.e(Context.ACTIVITY_SERVICE, "DocumentSnapshot added with ID: ${documentReference.id}")
                    finish()
                }
                .addOnFailureListener { e ->
                    Log.w(Context.ACTIVITY_SERVICE, "Error adding document", e)
                    //TODO ERROR MESSAGE
                }


    }


companion object {
    const val SELECT_PICTURE = 1
}
}