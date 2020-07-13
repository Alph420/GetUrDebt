package com.alphacorporations.givememymoney.View

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import butterknife.OnClick
import com.alphacorporations.givememymoney.R
import com.alphacorporations.givememymoney.View.DebtAdapter.DeleteTaskListener
import com.alphacorporations.givememymoney.model.Debt
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class ListDebtActivity : AppCompatActivity(), DeleteTaskListener {

    //PRIVATE VAR
    private lateinit var database: FirebaseDatabase
    private var adapter: DebtAdapter? = null

    //DEBT LIST
    var debtList:MutableList<Debt>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initList()
        setContentView(R.layout.activity_main)
        list_money.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        list_money.adapter = adapter

        add_debt.setOnClickListener { addDebt() }
    }

    private fun initList() {

    }

    @OnClick(R.id.add_debt)
    fun addDebt() {
        ActivityCompat.startActivity(this, Intent(this, AddDebtActivity::class.java), null)
    }



    override fun onDeleteDebt(debt: Debt?) {
        debtList!!.remove(debt)
        //model.deleteDebt(debt!!.id)
        updateTasks()
    }

    private fun updateTasks() {
        if (debtList!!.isEmpty()) {
            lbl_no_task.visibility = View.VISIBLE
            list_money.visibility = View.GONE
        } else {
            lbl_no_task.visibility = View.GONE
            list_money.visibility = View.VISIBLE
            adapter!!.updateTasks(debtList!!)
        }
    }
}