package com.alphacorporations.givememymoney.View.listActivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.alphacorporations.givememymoney.Constant.FIREBASE_COLLECTION_ID
import com.alphacorporations.givememymoney.R
import com.alphacorporations.givememymoney.View.AddDebtActivity
import com.alphacorporations.givememymoney.View.profilesActivity.ProfileUserActivity
import com.alphacorporations.givememymoney.model.Debt
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_list_debt.*


class ListDebtActivity : AppCompatActivity() {

    //GLOBAL VARIABLES
    private var debtList: MutableList<Debt> = arrayListOf()
    private val db = Firebase.firestore
    private var colletions: CollectionReference = db.collection(FIREBASE_COLLECTION_ID)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_debt)
        this.configureBottomView()

        initList()
    }

    fun configureBottomView() {
        activity_main_bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_list -> {

                }
                R.id.action_add -> {
                    ActivityCompat.startActivity(this, Intent(this, AddDebtActivity::class.java), null)
                }
                R.id.action_profil -> {
                    ActivityCompat.startActivity(this, Intent(this, ProfileUserActivity::class.java), null)
                }
            }
            true
        }
    }

    fun initList() {
        getDataFromFirebase()
    }


    private fun getDataFromFirebase() {
        progressBar_loading_debt.visibility = View.VISIBLE

        colletions.whereEqualTo("isDebt", true).get().addOnSuccessListener { result ->
            val listDebtFromFirebase: MutableList<Debt> = arrayListOf()

            for (document in result) {
                Log.d(Context.ACTIVITY_SERVICE, "${document.id} => ${document.data}")
                val img = document.data["img"].toString()
                val name = document.data["name"].toString()
                val reason = document.data["reason"].toString()
                val date = document.data["date"].toString()
                val amount = if (document.data["amount"]!!.toString() == "null") 0 else document.data["amount"].toString().toLong()
                listDebtFromFirebase.add(Debt(document.id, img, name, reason, date, amount))
            }

            debtList = listDebtFromFirebase
            progressBar_loading_debt.visibility = View.GONE
            setAndUseAdapter()
        }
    }

    private fun updateTasks() {
        if (debtList.isEmpty()) {
            lbl_no_task.visibility = View.VISIBLE
            list_money.visibility = View.GONE
        } else {
            lbl_no_task.visibility = View.GONE
            list_money.visibility = View.VISIBLE
        }
    }

    fun setAndUseAdapter() {
        val mLayoutManager = LinearLayoutManager(this)
        list_money.adapter = DebtAdapter(this, debtList)
        list_money.layoutManager = mLayoutManager
        list_money.adapter?.notifyDataSetChanged()
        updateTasks()
    }


    override fun onResume() {
        super.onResume()
        println("Resume")
        initList()
    }

    override fun onStart() {
        super.onStart()
        println("Start")
    }
}