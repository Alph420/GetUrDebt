package com.alphacorporations.givememymoney.View

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.alphacorporations.givememymoney.Constant
import com.alphacorporations.givememymoney.R
import com.alphacorporations.givememymoney.View.DebtAdapter.DebtViewHolder
import com.alphacorporations.givememymoney.event.OpenDebtEvent
import com.alphacorporations.givememymoney.model.Debt
import com.bumptech.glide.Glide
import org.greenrobot.eventbus.EventBus

/**
 *
 * Adapter which handles the list of tasks to display in the dedicated RecyclerView.
 *
 * @author Gaëtan HERFRAY
 */
class DebtAdapter
/**
 * Instantiates a new TasksAdapter.
 *
 * @param Debts the list of tasks the adapter deals with to set
 */ internal constructor(
        /**
         * The list of tasks the adapter deals with
         */
        private var debts: List<Debt?>,
        /**
         * The listener for when a task needs to be deleted
         */
        private val deleteTaskListener: DeleteTaskListener) : RecyclerView.Adapter<DebtViewHolder>() {

    /**
     * Updates the list of tasks the adapter deals with.
     *
     * @param debts the list of tasks the adapter deals with to set
     */
    fun updateTasks(debts: List<Debt?>) {
        this.debts = debts
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): DebtViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_money, viewGroup, false)
        return DebtViewHolder(view, deleteTaskListener)
    }

    override fun onBindViewHolder(debtViewHolder: DebtViewHolder, position: Int) {
        debts[position]?.let { debtViewHolder.bind(it) }

        //got to debt profile
        debtViewHolder.itemView.setOnClickListener { v: View? ->
            Constant.idDebt = debts[position]!!.id
            EventBus.getDefault().post(OpenDebtEvent((v)!!))
            println(Constant.idDebt)
        }
    }

    override fun getItemCount(): Int {
        return debts.size
    }

    /**
     * Listener for deleting tasks
     */
    interface DeleteTaskListener {
        /**
         * Called when a task needs to be deleted.
         *
         * @param debt the task that needs to be deleted
         */
        fun onDeleteDebt(debt: Debt?)
    }

    /**
     *
     * ViewHolder for task items in the tasks list
     *
     * @author Gaëtan HERFRAY
     */
    class DebtViewHolder(itemView: View, private val deleteTaskListener: DeleteTaskListener) : RecyclerView.ViewHolder(itemView) {
        private val debtImg: AppCompatImageView = itemView.findViewById(R.id.item_list_avatar)
        private val lblDebtName: TextView = itemView.findViewById(R.id.item_list_name)
        private val lblDebtDate: TextView = itemView.findViewById(R.id.item_list_date)
        private val lblDebtAmount: TextView = itemView.findViewById(R.id.item_list_amount)
        private val imgDelete: ImageButton = itemView.findViewById(R.id.item_list_delete_button)

        /**
         * Binds a task to the item view.
         *
         * @param debt the task to bind in the item view
         */
        fun bind(debt: Debt) {
            if (debt.img == null || debt.img == "") Glide.with(itemView).load(R.drawable.ic_person_green).centerCrop().into(debtImg) else Glide.with(itemView).load(debt.img).into(debtImg)
            lblDebtName.text = debt.name
            lblDebtDate.text = debt.date
            lblDebtAmount.text = debt.amount.toString() + "€"
            imgDelete.tag = debt
        }

        /**
         * Instantiates a new TaskViewHolder.
         *
         * @param itemView           the view of the task item
         * @param deleteTaskListener the listener for when a task needs to be deleted to set
         */
        init {

            //Delete Debt and confirmation
            imgDelete.setOnClickListener { view: View ->
                val builder1 = AlertDialog.Builder(view.context)
                builder1.setMessage("Veux tu vraiment supprimer cet dette ?")
                builder1.setCancelable(true)
                builder1.setPositiveButton(
                        "Supprimer"
                ) { dialog, _ ->
                    val tag = view.tag
                    if (tag is Debt) {
                        deleteTaskListener.onDeleteDebt(tag)
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