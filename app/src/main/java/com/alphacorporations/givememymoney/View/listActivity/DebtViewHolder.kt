package com.alphacorporations.givememymoney.View.listActivity

import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.alphacorporations.givememymoney.Constant
import com.alphacorporations.givememymoney.Constant.FIREBASE_IMG_MARGIN
import com.alphacorporations.givememymoney.Constant.FIREBASE_IMG_RADIUS
import com.alphacorporations.givememymoney.R
import com.alphacorporations.givememymoney.View.LoadingActivity
import com.alphacorporations.givememymoney.model.Debt
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import jp.wasabeef.glide.transformations.RoundedCornersTransformation


/**
Created by Alph4 le 15/07/2020.
Projet: Give Me My Money
 **/
class DebtViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_money, parent, false)) {

    private val db = Firebase.firestore
    private var debtImg: ImageView? = null
    private var lblDebtName: TextView? = null
    private var lblDebtDate: TextView? = null
    private var lblDebtAmount: TextView? = null
    private var imgDelete: ImageButton? = null

    init {
        debtImg = itemView.findViewById(R.id.item_list_avatar) as ImageView
        lblDebtName = itemView.findViewById(R.id.item_list_name) as TextView
        lblDebtDate = itemView.findViewById(R.id.item_list_date) as TextView
        lblDebtAmount = itemView.findViewById(R.id.item_list_amount) as TextView
        imgDelete = itemView.findViewById(R.id.item_list_delete_button) as ImageButton
    }

    fun bind(debt: Debt, pos: Int, list: MutableList<Debt>) {
        /**if debtImg doesn't exist draw the little white person**/
        if (debt.img.equals("null")) debtImg?.let {
            Glide.with(itemView.context).load(R.drawable.ic_person_white).into(it)
        }
        /**else draw de debtImg**/
        else {
            debtImg?.let { Glide.with(itemView).load(debt.img).transform(CircleCrop()).into(it) }
        }

        lblDebtName?.text = debt.name
        lblDebtDate?.text = debt.date
        lblDebtAmount?.text = debt.amount.toString().plus("€")

        /**Confirmation delete debt**/
        imgDelete!!.setOnClickListener { view: View ->
            val builder1 = AlertDialog.Builder(view.context)
            builder1.setMessage("Veux tu vraiment supprimer cet dette ?")
            builder1.setCancelable(true)
            builder1.setPositiveButton(
                    "Supprimer"
            ) { dialog, _ ->
                list.removeAt(pos)
                dialog.cancel()
                db.collection(Constant.FIREBASE_COLLECTION_ID).document(debt.id.toString()).delete()
                view.context.startActivity(Intent(view.context, LoadingActivity::class.java))

            }
            builder1.setNegativeButton(
                    "Annuler"
            ) { dialog: DialogInterface, _: Int -> dialog.cancel() }
            val alert11 = builder1.create()
            alert11.show()
        }
    }
}