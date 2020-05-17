package com.alphacorporations.givememymoney.Controler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alphacorporations.givememymoney.R;
import com.alphacorporations.givememymoney.events.DeleteDebtEvent;
import com.alphacorporations.givememymoney.model.Debt;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alph4 le 17/05/2020.
 * Projet: Give Me My Money
 **/
public class MyDebtRecyclerViewAdapter extends RecyclerView.Adapter<MyDebtRecyclerViewAdapter.ViewHolder> {

    private final List<Debt> mDebts;

    public MyDebtRecyclerViewAdapter(List<Debt> debts) {
        mDebts = debts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_money, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Debt debt = mDebts.get(position);

        holder.mDebtsAvatar.setColorFilter(debt.getImg());
        holder.mDebtsName.setText(debt.getName());
        holder.mDebtsObject.setText(debt.getObject());
        holder.mDebtsDate.setText(debt.getDate());
        holder.mDebtsAmount.setText(debt.getAmount() + "$");
        holder.mDeleteButton.setOnClickListener(v -> EventBus.getDefault().post(new DeleteDebtEvent(debt)));

    }

    @Override
    public int getItemCount() {
        return mDebts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_list_avatar)
        public ImageView mDebtsAvatar;
        @BindView(R.id.item_list_name)
        public TextView mDebtsName;
        @BindView(R.id.item_list_object)
        public TextView mDebtsObject;
        @BindView(R.id.item_list_date)
        public TextView mDebtsDate;
        @BindView(R.id.item_list_amount)
        public TextView mDebtsAmount;
        @BindView(R.id.item_list_delete_button)
        public ImageButton mDeleteButton;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }
}
