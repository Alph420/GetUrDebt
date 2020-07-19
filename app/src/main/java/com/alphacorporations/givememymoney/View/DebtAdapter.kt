package com.alphacorporations.givememymoney.View

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alphacorporations.givememymoney.model.Debt


class DebtAdapter(private val c:Context,private val list: MutableList<Debt>?)
    : RecyclerView.Adapter<DebtViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DebtViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return DebtViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int=list!!.size

    override fun onBindViewHolder(holder: DebtViewHolder, position: Int) {
        val debt: Debt = list!![position]
        holder.bind(debt,position,list)
    }



}