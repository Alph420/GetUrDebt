package com.alphacorporations.givememymoney.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import com.alphacorporations.givememymoney.R;
import com.alphacorporations.givememymoney.ViewModel.Injection;
import com.alphacorporations.givememymoney.ViewModel.ViewModelFactory;
import com.alphacorporations.givememymoney.model.Debt;
import com.alphacorporations.givememymoney.model.ListDebtViewModel;
import com.alphacorporations.givememymoney.model.database.DebtDatabase;
import com.alphacorporations.givememymoney.model.database.dao.DebtDao;

public class AddDebtActivity extends AppCompatActivity {

    DebtDatabase mDatabase;
    DebtDao debtDao;
    ListDebtViewModel mListDebtViewModel;
    String date;

    final static int SELECT_PICTURE = 1;

    ImageView avatarDebt;
    TextView firstName;
    TextView lastName;
    TextView object;
    TextView amount;
    String avatar;

    Button mButtonSave;

    Boolean setDate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_debt);

        mDatabase = DebtDatabase.getInstance(this);
        debtDao = mDatabase.debtDao();
        this.configureViewModel();

        avatarDebt = findViewById(R.id.avatar);
        avatarDebt.setOnClickListener(v -> selectAvatar());


        Button mdatePicker = findViewById(R.id.date_debt);
        mdatePicker.setOnClickListener(v -> date());

        firstName = findViewById(R.id.first_name_debt);
        lastName = findViewById(R.id.last_name_debt);
        object = findViewById(R.id.object_debt);
        amount = findViewById(R.id.amount_debt);

        mButtonSave = findViewById(R.id.save_debt);
        mButtonSave.setEnabled(false);
        mButtonSave.setTextColor(Color.RED);

        firstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.length() >= 1) {
                    mButtonSave.setEnabled(true);
                    mButtonSave.setTextColor(Color.WHITE);
                }
                if (s.length() == 0) {
                    mButtonSave.setEnabled(false);
                    mButtonSave.setTextColor(Color.RED);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= 1) {
                    mButtonSave.setEnabled(true);
                    mButtonSave.setTextColor(Color.WHITE);
                }
                if (s.length() == 0) {
                    mButtonSave.setEnabled(false);
                    mButtonSave.setTextColor(Color.RED);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 1) {
                    mButtonSave.setEnabled(true);
                    mButtonSave.setTextColor(Color.WHITE);
                }
                if (s.length() == 0) {
                    mButtonSave.setEnabled(false);
                    mButtonSave.setTextColor(Color.RED);
                }
            }
        });

        mButtonSave.setOnClickListener(v -> saving());

    }

    public void saving() {
        long id = 0;

        String name = firstName.getText() + " " + lastName.getText();
        String objectDebt;
        if (object.getText().equals("")) objectDebt = null;
        else objectDebt = object.getText().toString();

        String amountField = amount.getText().toString();
        int amountDebt = amountField.equals("") ? 0:Integer.parseInt(amountField);




        if (setDate) {
            Debt debt = new Debt(id, avatar, name, objectDebt, date, amountDebt);
            this.mListDebtViewModel.createDebt(debt);
            finish();

        } else {
            Debt debt = new Debt(id, avatar, name, objectDebt, null, amountDebt);
            this.mListDebtViewModel.createDebt(debt);
            finish();
        }

    }

    public void selectAvatar() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, ""), SELECT_PICTURE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            //TODO use firebase
            final Uri imageUri = data.getData();
            avatar = imageUri.toString();
            avatarDebt.setImageURI(imageUri);

        }
    }

    public void date() {
        final AlertDialog.Builder builderDatePicker = new AlertDialog.Builder(this);
        DatePicker picker = new DatePicker(this);
        picker.setCalendarViewShown(false);
        builderDatePicker.setView(picker);

        builderDatePicker.setPositiveButton("OK", (dialog, which) -> {
            int year = picker.getYear();
            int mon = picker.getMonth();
            int day = picker.getDayOfMonth();
            date = day + "/" + mon + "/" + year;
            setDate = true;
        });
        builderDatePicker.setNegativeButton(
                "Annuler",
                (dialog, id) -> dialog.cancel());
        builderDatePicker.show();
    }

    private void configureViewModel() {
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(this);
        this.mListDebtViewModel = ViewModelProviders.of(this, mViewModelFactory).get(ListDebtViewModel.class);
    }

}
