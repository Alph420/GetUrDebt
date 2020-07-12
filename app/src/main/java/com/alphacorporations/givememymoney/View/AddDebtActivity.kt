package com.alphacorporations.givememymoney.View

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.alphacorporations.givememymoney.R
import com.alphacorporations.givememymoney.model.Debt
import kotlinx.android.synthetic.main.activity_add_debt.*

class AddDebtActivity : AppCompatActivity() {

    private var avatarUri: String? = null
    private var date: String? = null
    private var picker: DatePicker? = null
    var setDate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_debt)
        configureViewModel()
        initUI()
        avatar!!.setOnClickListener { v: View? -> selectAvatar() }
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
                    save_debt!!.setTextColor(Color.GREEN)
                }
                if (s.isEmpty()) {
                    save_debt!!.isEnabled = false
                    save_debt!!.setTextColor(Color.RED)
                }
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.isNotEmpty()) {
                    save_debt!!.isEnabled = true
                    save_debt!!.setTextColor(Color.GREEN)
                }
                if (s.isEmpty()) {
                    save_debt!!.isEnabled = false
                    save_debt!!.setTextColor(Color.RED)
                }
            }

            override fun afterTextChanged(s: Editable) {
                if (s.isNotEmpty()) {
                    save_debt!!.isEnabled = true
                    save_debt!!.setTextColor(Color.GREEN)
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

    fun initUI() {
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
            //TODO use firebase
            val imageUri = data!!.data
            avatarUri = imageUri.toString()
            avatar!!.setImageURI(imageUri)
        }
    }

    fun date() {
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

    fun saving() {
        val id: Long = 0
        val name = first_name_debt!!.text.toString() + " " + last_name_debt!!.text
        val objectDebt: String? = if (object_debt.toString() == "") null else object_debt!!.text.toString()
        val amountField = amount_debt!!.text.toString()
        val amountDebt = if (amountField == "") 0 else amountField.toInt()
        if (setDate) {
            //val debt = Debt(id, avatarUri, name, objectDebt, date, amountDebt)
            //model.createDebt(debt)
            finish()
        } else {
           // val debt = Debt(id, avatarUri, name, objectDebt, null, amountDebt)
            //model.createDebt(debt)
            finish()
        }
    }

    private fun configureViewModel() {
        //val mViewModelFactory = Injection.provideViewModelFactory(this)
        //model = ViewModelProvider(this, mViewModelFactory).get(ListDebtViewModel::class.java)
    }

    companion object {
        const val SELECT_PICTURE = 1
    }
}