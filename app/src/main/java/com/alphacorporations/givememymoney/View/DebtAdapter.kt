package com.alphacorporations.givememymoney.View

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.alphacorporations.givememymoney.R
import com.alphacorporations.givememymoney.model.Debt


class DebtAdapter(context: Context, DebtItemList: MutableList<Debt>) : BaseAdapter(){

    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var itemList = DebtItemList

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        //if (itemList.get(position).img == null || itemList.get(position).img == "") Glide.with().load(R.drawable.ic_person_green).centerCrop().into(debtImg) else Glide.with(itemView).load(debt.img).into(debtImg)
        var lblDebtName: String? = itemList[position].name.toString()
        var lblDebtDate: String? = itemList[position].date.toString()
        var lblDebtAmount: String = itemList[position].amount.toString().plus("€")
        val view: View
        val vh: ListRowHolder
        if (convertView == null) {
            view = mInflater.inflate(R.layout.item_money, parent, false)
            vh = ListRowHolder(view)
            view.tag = vh
        } else {
            view = convertView
        }
        return view
    }

    override fun getItem(index: Int): Any {
        return itemList.get(index)
    }

    override fun getItemId(index: Int): Long {
        return index.toLong()
    }

    override fun getCount(): Int {
        return itemList.size
    }

    private class ListRowHolder(row: View?) {
        private val debtImg: ImageView = row!!.findViewById(R.id.item_list_avatar) as ImageView
        private val lblDebtName: TextView = row!!.findViewById(R.id.item_list_name) as TextView
        private val lblDebtDate: TextView = row!!.findViewById(R.id.item_list_date) as TextView
        private val lblDebtAmount: TextView = row!!.findViewById(R.id.item_list_amount) as TextView
        private val imgDelete: ImageButton = row!!.findViewById(R.id.item_list_delete_button) as ImageButton

        /**Instantiates a new TaskViewHolder.**/
        init {

            /**Confirmation delete debt**/
            imgDelete.setOnClickListener { view: View ->
                val builder1 = AlertDialog.Builder(view.context)
                builder1.setMessage("Veux tu vraiment supprimer cet dette ?")
                builder1.setCancelable(true)
                builder1.setPositiveButton(
                        "Supprimer"
                ) { dialog, _ ->
                    val tag = view.tag
                    if (tag is Debt) {

                    }
                    dialog.cancel()
                }
                builder1.setNegativeButton(
                        "Annuler"
                ) { dialog: DialogInterface, _: Int -> dialog.cancel() }
                val alert11 = builder1.create()
                alert11.show()
            }
        }
    }

}