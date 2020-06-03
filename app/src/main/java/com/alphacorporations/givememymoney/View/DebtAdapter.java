package com.alphacorporations.givememymoney.View;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.alphacorporations.givememymoney.Constant;
import com.alphacorporations.givememymoney.R;
import com.alphacorporations.givememymoney.event.OpenDebtEvent;
import com.alphacorporations.givememymoney.model.Debt;
import com.alphacorporations.givememymoney.model.database.DebtDatabase;
import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * <p>Adapter which handles the list of tasks to display in the dedicated RecyclerView.</p>
 *
 * @author Gaëtan HERFRAY
 */
public class DebtAdapter extends RecyclerView.Adapter<DebtAdapter.DebtViewHolder> {

    DebtDatabase mDatabase;

    /**
     * The list of tasks the adapter deals with
     */
    @NonNull
    private List<Debt> debts;

    /**
     * The listener for when a task needs to be deleted
     */
    @NonNull
    private final DeleteTaskListener deleteTaskListener;

    /**
     * Instantiates a new TasksAdapter.
     *
     * @param Debts the list of tasks the adapter deals with to set
     */
    DebtAdapter(@NonNull final List<Debt> Debts, @NonNull final DeleteTaskListener deleteTaskListener, DebtDatabase db) {
        this.debts = Debts;
        this.deleteTaskListener = deleteTaskListener;
        this.mDatabase = db;
    }

    /**
     * Updates the list of tasks the adapter deals with.
     *
     * @param debts the list of tasks the adapter deals with to set
     */
    void updateTasks(@NonNull final List<Debt> debts) {
        this.debts = debts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DebtViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_money, viewGroup, false);
        return new DebtViewHolder(view, deleteTaskListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DebtViewHolder debtViewHolder, int position) {
        debtViewHolder.bind(debts.get(position));

        //got to debt profile
        debtViewHolder.itemView.setOnClickListener(v -> {
            Constant.idDebt = debts.get(position).getId();
            EventBus.getDefault().post(new OpenDebtEvent(v));
        });
    }

    @Override
    public int getItemCount() {
        return debts.size();
    }

    /**
     * Listener for deleting tasks
     */
    public interface DeleteTaskListener {
        /**
         * Called when a task needs to be deleted.
         *
         * @param debt the task that needs to be deleted
         */
        void onDeleteDebt(Debt debt);
    }

    /**
     * <p>ViewHolder for task items in the tasks list</p>
     *
     * @author Gaëtan HERFRAY
     */
    static class DebtViewHolder extends RecyclerView.ViewHolder {

        private final AppCompatImageView debtImg;
        private final TextView lblDebtName;
        private final TextView lblDebtObject;
        private final TextView lblDebtDate;
        private final TextView lblDebtAmount;
        private final ImageButton imgDelete;
        private final DeleteTaskListener deleteTaskListener;

        /**
         * Instantiates a new TaskViewHolder.
         *
         * @param itemView           the view of the task item
         * @param deleteTaskListener the listener for when a task needs to be deleted to set
         */
        DebtViewHolder(@NonNull View itemView, @NonNull DeleteTaskListener deleteTaskListener) {
            super(itemView);

            this.deleteTaskListener = deleteTaskListener;

            debtImg = itemView.findViewById(R.id.item_list_avatar);
            lblDebtName = itemView.findViewById(R.id.item_list_name);
            lblDebtObject = itemView.findViewById(R.id.item_list_object);
            lblDebtDate = itemView.findViewById(R.id.item_list_date);
            lblDebtAmount = itemView.findViewById(R.id.item_list_amount);
            imgDelete = itemView.findViewById(R.id.item_list_delete_button);

            //Delete Debt and confirmation
            imgDelete.setOnClickListener(view -> {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());
                builder1.setMessage("Veux tu vraiment supprimer cet dette ?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Supprimer",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                final Object tag = view.getTag();
                                if (tag instanceof Debt) {
                                    DebtViewHolder.this.deleteTaskListener.onDeleteDebt((Debt) tag);
                                }
                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "Annuler",
                        (dialog, id) -> dialog.cancel());

                AlertDialog alert11 = builder1.create();
                alert11.show();
            });

        }

        /**
         * Binds a task to the item view.
         *
         * @param debt the task to bind in the item view
         */
        void bind(Debt debt) {
            if (debt.getImg() == null || debt.getImg().equals(""))
                Glide.with(itemView).load(R.drawable.ic_person).centerCrop().into(debtImg);
            else
                Glide.with(itemView).load(debt.getImg()).into(debtImg);

            lblDebtName.setText(debt.getName());
            lblDebtObject.setText(debt.getObject());
            lblDebtDate.setText(debt.getDate());
            lblDebtAmount.setText(debt.getAmount() + "€");
            imgDelete.setTag(debt);
        }
    }
}
