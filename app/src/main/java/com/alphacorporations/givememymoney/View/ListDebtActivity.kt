package com.alphacorporations.givememymoney.View

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import butterknife.OnClick
import com.alphacorporations.givememymoney.R
import com.alphacorporations.givememymoney.event.DeleteMeetingEvent
import com.alphacorporations.givememymoney.model.Debt
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class ListDebtActivity : AppCompatActivity() {

    //GLOBAL VARIABLES
    var debtList: MutableList<Debt> = mutableListOf()
    lateinit var adapter: DebtAdapter
    private val db = Firebase.firestore
    private lateinit var collections:CollectionReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        collections = db.collection("DebtList")
        initList(collections)
        setAdapter()

        add_debt.setOnClickListener { addDebt() }
    }

     fun initList(collection: CollectionReference? = null) {

        collection!!.get().addOnSuccessListener { result ->
            for (document in result) {
                Log.d(Context.ACTIVITY_SERVICE, "${document.id} => ${document.data}")
                val img = document.data["img"].toString()
                val name = document.data["name"].toString()
                val reason = document.data["reason"].toString()
                val date = document.data["date"].toString()
                val amount = document.data["amount"].toString()

                debtList.add(Debt(document.id, img, name, reason, date, amount.toLong()))
            }
            updateTasks()
        }
                .addOnFailureListener { exception ->
                    Log.w(Context.ACTIVITY_SERVICE, "Error getting documents.", exception)
                }
    }

    private fun setAdapter() {
        val mLayoutManager = LinearLayoutManager(this)
        list_money.layoutManager = mLayoutManager
        list_money.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        adapter = DebtAdapter(debtList)
        list_money.adapter = DebtAdapter(debtList)
    }

    @OnClick(R.id.add_debt)
    fun addDebt() {
        ActivityCompat.startActivity(this, Intent(this, AddDebtActivity::class.java), null)
    }

     fun updateTasks() {
        if (debtList.isEmpty()) {
            lbl_no_task.visibility = View.VISIBLE
            list_money.visibility = View.GONE
        } else {
            lbl_no_task.visibility = View.GONE
            list_money.visibility = View.VISIBLE
        }
    }



    override fun onPostResume() {
        super.onPostResume()
        initList()
    }
}