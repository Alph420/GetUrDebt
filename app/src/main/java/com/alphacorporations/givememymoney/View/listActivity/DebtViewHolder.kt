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
import com.alphacorporations.givememymoney.Constant.DEBT_ID
import com.alphacorporations.givememymoney.Constant.DEVISE
import com.alphacorporations.givememymoney.Constant.FIREBASE_IMG_DEBT_RESIZE
import com.alphacorporations.givememymoney.R
import com.alphacorporations.givememymoney.View.LoadingActivity
import com.alphacorporations.givememymoney.event.OpenDebtEvent
import com.alphacorporations.givememymoney.model.Debt
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import jp.wasabeef.glide.transformations.CropSquareTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.activity_profile_user.*
import org.greenrobot.eventbus.EventBus


/**
Created by Alph4 le 15/07/2020.
Projet: Give Me My Money
 **/
class DebtViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_money, parent, false)) {

    private val db = Firebase.firestore
    private var mStorageRef: StorageReference = FirebaseStorage.getInstance().reference
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

        mStorageRef
                .child("debt_images/${debt.id}${FIREBASE_IMG_DEBT_RESIZE}")
                .downloadUrl
                .addOnSuccessListener {
                    debtImg?.let { it1 ->
                        Glide
                                .with(itemView)
                                .load(it)
                                .transform(CropSquareTransformation())
                                .into(it1)
                    }
                }
                .addOnFailureListener { debtImg?.let { it1 -> Glide.with(itemView.context).load(R.drawable.ic_person_white).into(it1) } }

        lblDebtName?.text = debt.name
        lblDebtDate?.text = debt.date
        lblDebtAmount?.text = debt.amount.toString().plus(DEVISE)

        /**Confirmation delete debt**/
        imgDelete!!.setOnClickListener { deleteDebt(debt, list, pos, it) }

        itemView.setOnClickListener {
            openDebt(debt.id!!, it)
        }
    }

    private fun deleteDebt(debt: Debt, list: MutableList<Debt>, pos: Int, view: View) {
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

    private fun openDebt(id: String, it: View) {
        DEBT_ID = id
        EventBus.getDefault().post(OpenDebtEvent().OpenDebtEvent(it))
    }
}