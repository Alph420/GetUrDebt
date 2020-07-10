package com.alphacorporations.givememymoney.View

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.alphacorporations.givememymoney.Constant
import com.alphacorporations.givememymoney.R
import com.alphacorporations.givememymoney.ViewModel.Injection
import com.alphacorporations.givememymoney.model.Debt
import com.alphacorporations.givememymoney.model.database.DebtDatabase
import com.alphacorporations.givememymoney.model.database.DebtDatabase.Companion.getInstance
import com.alphacorporations.givememymoney.model.database.dao.DebtDao
import com.alphacorporations.givememymoney.model.repositories.ProfileDebtViewModel

class ProfileDebtActivity : AppCompatActivity() {
    //UI COMPONENTS
    var mImageView: ImageView? = null
    var mNameDebt: EditText? = null
    var mDebtObject: EditText? = null
    var mDebtAmount: EditText? = null
    var mSaveBtn: Button? = null
    var mDatabase: DebtDatabase? = null
    var debtDao: DebtDao? = null
    var mProfileDebtViewModel: ProfileDebtViewModel? = null
    private var debt: Debt? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_debt)
        mDatabase = getInstance(this)
        debtDao = mDatabase!!.debtDao()
        configureViewModel()
        populateList()
        initUI()
        mImageView!!.setOnClickListener { v: View? -> selectAvatar() }
        mSaveBtn!!.setOnClickListener { v: View? -> save() }
    }

    private fun initUI() {
        mImageView = findViewById(R.id.avatar)
        mNameDebt = findViewById(R.id.name_debt)
        mDebtObject = findViewById(R.id.object_debt)
        mDebtAmount = findViewById(R.id.amount_debt)
        mSaveBtn = findViewById(R.id.save_debt)
    }

    private fun initDebtProfile() {
        if (debt!!.img == null) mImageView!!.setImageResource(R.drawable.ic_person_green) else mImageView!!.setImageURI(Uri.parse(debt!!.img))
        mNameDebt!!.setText(debt!!.name)
        mDebtObject!!.setText(debt!!.`object`)
        mDebtAmount!!.setText(debt!!.amount.toString())
    }

    private fun configureViewModel() {
        val mViewModelFactory = Injection.provideViewModelFactory(this)
        mProfileDebtViewModel = ViewModelProviders.of(this, mViewModelFactory).get(ProfileDebtViewModel::class.java)
    }

    private fun populateList() {
        val debtObserver = Observer { debtList: Debt? ->
            if (debtList != null) {
                debt = debtList
                initDebtProfile()
            }
        }
        mProfileDebtViewModel!!.getDebtsById(Constant.idDebt)!!.observe(this, debtObserver)
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
            mImageView!!.setImageURI(imageUri)
            debt!!.img = imageUri.toString()
        }
    }

    private fun save() {
        debt!!.name = mNameDebt!!.text.toString()
        debt!!.`object` = mDebtObject!!.text.toString()
        val amountstring = mDebtAmount!!.text.toString()
        val amount = amountstring.toInt()
        debt!!.amount = amount
        mProfileDebtViewModel!!.updateDebt(debt)
        finish()
    }

    companion object {
        const val SELECT_PICTURE = 1
    }
}