package com.alphacorporations.givememymoney.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.alphacorporations.givememymoney.Constant;
import com.alphacorporations.givememymoney.R;
import com.alphacorporations.givememymoney.ViewModel.Injection;
import com.alphacorporations.givememymoney.ViewModel.ViewModelFactory;
import com.alphacorporations.givememymoney.model.Debt;
import com.alphacorporations.givememymoney.model.MainViewModel;
import com.alphacorporations.givememymoney.model.database.DebtDatabase;
import com.alphacorporations.givememymoney.model.database.dao.DebtDao;

import java.util.ArrayList;
import java.util.List;

public class ProfileDebtActivity extends AppCompatActivity {


    //UI COMPONENTS
    ImageView mImageView;
    EditText mNameDebt;
    EditText mDebtObject;
    EditText mDebtAmount;
    Button mSaveBtn;


    DebtDatabase mDatabase;
    DebtDao debtDao;
    MainViewModel mMainViewModel;

    private List<Debt> mDebtList = Constant.mDebtList;
    private Debt debt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_debt);

        mDatabase = DebtDatabase.getInstance(this);
        debtDao = mDatabase.debtDao();

        this.configureViewModel();
        getDebtFromList();
        initUI();
        initDebtProfile();


        mSaveBtn.setOnClickListener(v -> save());

    }

    private void initUI() {
        mImageView = findViewById(R.id.avatar);
        mNameDebt = findViewById(R.id.name_debt);
        mDebtObject = findViewById(R.id.object_debt);
        mDebtAmount = findViewById(R.id.amount_debt);
        mSaveBtn = findViewById(R.id.save_debt);
    }

    private void initDebtProfile() {
        mNameDebt.setText(debt.getName());
        mDebtObject.setText(debt.getObject());
        mDebtAmount.setText(String.valueOf(debt.getAmount()));
    }


    private void configureViewModel() {
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(this);
        this.mMainViewModel = ViewModelProviders.of(this, mViewModelFactory).get(MainViewModel.class);
    }

    private void getDebtFromList() {
        for (Debt mdebt : mDebtList) {
            if (mdebt.getId() == Constant.idDebt) debt = mdebt;
        }
    }

    private void save() {
        debt.setName(mNameDebt.getText().toString());
        debt.setObject(mDebtObject.getText().toString());
        String amountstring = mDebtAmount.getText().toString();
        int amount = Integer.parseInt(amountstring);
        debt.setAmount(amount);

        this.mMainViewModel.updateDebt(debt);
        finish();
    }

}
