package com.alphacorporations.givememymoney.View

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import butterknife.OnClick
import com.alphacorporations.givememymoney.R
import com.alphacorporations.givememymoney.model.Debt
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class ListDebtActivity : AppCompatActivity() {

    //GLOBAL VARIABLES
    var debtList: MutableList<Debt> = mutableListOf()
    lateinit var adapter: DebtAdapter
    var mDatabase: DatabaseReference? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mDatabase = FirebaseDatabase.getInstance().reference
        adapter = DebtAdapter(this, debtList)
        list_money.adapter = adapter

        val itemListener: ValueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                addDataToList(dataSnapshot)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Item failed, log a message
                Log.w("MainActivity", "loadItem:onCancelled", databaseError.toException())
            }

        }

        mDatabase!!.orderByKey().addListenerForSingleValueEvent(itemListener)

        add_debt.setOnClickListener { addDebt() }
    }


    fun addDataToList(dataSnapshot: DataSnapshot) {

        val items = dataSnapshot.children.iterator()
        //Check if current database contains any collection
        if (items.hasNext()) {
            val toDoListened = items.next()
            val itemsIterator = toDoListened.children.iterator()

            //check if the collection has any to do items or not
            while (itemsIterator.hasNext()) {

                //get current item
                val currentItem = itemsIterator.next()
                val debtItem = Debt.create()

                //get current data in a map
                val map = currentItem.getValue() as HashMap<String, Any>

                //key will return Firebase ID
                debtItem.id = currentItem.key
                debtItem.img = map["img"] as String?
                debtItem.name = map["name"] as String?
                debtItem.reason = map["reason"] as String?
                debtItem.date = map["date"] as String?
                debtItem.amount = map["amount"] as Long?
                Log.v("MainActivity", debtItem.id + debtItem.name)
                debtList.add(debtItem)
            }
        }
        adapter.notifyDataSetChanged()
    }

    @OnClick(R.id.add_debt)
    fun addDebt() {
        ActivityCompat.startActivity(this, Intent(this, AddDebtActivity::class.java), null)
    }


    fun onDeleteDebt(debt: Debt?) {
        debtList.remove(debt)
        updateTasks()
    }

    private fun updateTasks() {
        if (debtList.isEmpty()) {
            lbl_no_task.visibility = View.VISIBLE
            list_money.visibility = View.GONE
        } else {
            lbl_no_task.visibility = View.GONE
            list_money.visibility = View.VISIBLE
            adapter.notifyDataSetChanged()
        }
    }
}