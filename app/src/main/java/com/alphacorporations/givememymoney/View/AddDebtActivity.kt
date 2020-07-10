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
import androidx.lifecycle.ViewModelProviders
import com.alphacorporations.givememymoney.R
import com.alphacorporations.givememymoney.ViewModel.Injection
import com.alphacorporations.givememymoney.model.Debt
import com.alphacorporations.givememymoney.model.ListDebtViewModel
import com.alphacorporations.givememymoney.model.database.DebtDatabase
import com.alphacorporations.givememymoney.model.database.DebtDatabase.Companion.getInstance
import com.alphacorporations.givememymoney.model.database.dao.DebtDao
import kotlinx.android.synthetic.main.activity_add_debt.*

class AddDebtActivity : AppCompatActivity() {
    var mDatabase: DebtDatabase? = null
    var debtDao: DebtDao? = null
    var mListDebtViewModel: ListDebtViewModel? = null
    var date: String? = null
    var avatarDebt: ImageView? = null
    var firstName: TextView? = null
    var lastName: TextView? = null
    var `object`: TextView? = null
    var amount: TextView? = null
    var avatar: String? = null
    var switchDate: Switch? = null
    var dateBtn: Button? = null
    var mButtonSave: Button? = null
    var picker: DatePicker? = null
    var setDate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_debt)
        mDatabase = getInstance(this)
        debtDao = mDatabase!!.debtDao()
        configureViewModel()
        initUI()
        avatarDebt!!.setOnClickListener { v: View? -> selectAvatar() }
        switchDate!!.setOnCheckedChangeListener { _: CompoundButton?, _: Boolean ->
            if (switchDate!!.isChecked) {
                println("VISIBLE")
                dateBtn!!.visibility = View.VISIBLE
            } else {
                dateBtn!!.visibility = View.INVISIBLE
            }
        }
        firstName!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                if (s.length >= 1) {
                    mButtonSave!!.isEnabled = true
                    mButtonSave!!.setTextColor(Color.GREEN)
                }
                if (s.isEmpty()) {
                    mButtonSave!!.isEnabled = false
                    mButtonSave!!.setTextColor(Color.RED)
                }
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.length >= 1) {
                    mButtonSave!!.isEnabled = true
                    mButtonSave!!.setTextColor(Color.GREEN)
                }
                if (s.isEmpty()) {
                    mButtonSave!!.isEnabled = false
                    mButtonSave!!.setTextColor(Color.RED)
                }
            }

            override fun afterTextChanged(s: Editable) {
                if (s.length >= 1) {
                    mButtonSave!!.isEnabled = true
                    mButtonSave!!.setTextColor(Color.GREEN)
                }
                if (s.isEmpty()) {
                    mButtonSave!!.isEnabled = false
                    mButtonSave!!.setTextColor(Color.RED)
                }
            }
        })
        dateBtn!!.setOnClickListener { date() }
        mButtonSave!!.setOnClickListener { saving() }
    }

    fun initUI() {
        avatarDebt = findViewById(R.id.avatar)
        firstName = findViewById(R.id.first_name_debt)
        lastName = findViewById(R.id.last_name_debt)
        `object` = findViewById(R.id.object_debt)
        amount = findViewById(R.id.amount_debt)
        switchDate = findViewById(R.id.switch_date)
        dateBtn = findViewById(R.id.date_debt)
        mButtonSave = findViewById(R.id.save_debt)
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
            avatar = imageUri.toString()
            avatarDebt!!.setImageURI(imageUri)
        }
    }

    fun date() {
        val builderDatePicker = AlertDialog.Builder(this)
        picker = DatePicker(this)
        picker!!.calendarViewShown = false
        builderDatePicker.setView(picker)
        builderDatePicker.setPositiveButton("OK") { dialog: DialogInterface?, which: Int ->
            val year = picker!!.year
            val mon = picker!!.month
            val day = picker!!.dayOfMonth
            date = "$day/$mon/$year"
            setDate = true
        }
        builderDatePicker.setNegativeButton(
                "Annuler"
        ) { dialog: DialogInterface, id: Int -> dialog.cancel() }
        builderDatePicker.show()
    }

    fun saving() {
        val id: Long = 0
        val name = firstName!!.text.toString() + " " + lastName!!.text
        val objectDebt: String? = if (`object`!!.text == "") null else `object`!!.text.toString()
        val amountField = amount!!.text.toString()
        val amountDebt = if (amountField == "") 0 else amountField.toInt()
        if (setDate) {
            val debt = Debt(id, avatar, name, objectDebt, date, amountDebt)
            mListDebtViewModel!!.createDebt(debt)
            finish()
        } else {
            val debt = Debt(id, avatar, name, objectDebt, null, amountDebt)
            mListDebtViewModel!!.createDebt(debt)
            finish()
        }
    }

    private fun configureViewModel() {
        val mViewModelFactory = Injection.provideViewModelFactory(this)
        mListDebtViewModel = ViewModelProviders.of(this, mViewModelFactory).get(ListDebtViewModel::class.java)
    }

    companion object {
        const val SELECT_PICTURE = 1
    }
}