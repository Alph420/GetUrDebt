package com.alphacorporations.givememymoney;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import com.alphacorporations.givememymoney.View.DebtAdapter;
import com.alphacorporations.givememymoney.ViewModel.Injection;
import com.alphacorporations.givememymoney.ViewModel.ViewModelFactory;
import com.alphacorporations.givememymoney.model.Debt;
import com.alphacorporations.givememymoney.model.MainViewModel;
import com.alphacorporations.givememymoney.model.database.DebtDatabase;
import com.alphacorporations.givememymoney.model.database.dao.DebtDao;

import java.util.ArrayList;
import java.util.List;

public class ProfileDebtActivity extends AppCompatActivity {

    DebtDatabase mDatabase;
    DebtDao debtDao;
    MainViewModel mMainViewModel;

    private DebtAdapter adapter;
    private List<Debt> mDebtList = new ArrayList<>();
    private LiveData debt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_debt);

        mDatabase = DebtDatabase.getInstance(this);
        debtDao = mDatabase.debtDao();
        this.configureViewModel();


        //Get Id of Neighbour Selected
        Intent intent = getIntent();

        if (intent != null) {
            if (intent.hasExtra("DebtId")) {
                debt = this.mMainViewModel.getDebtsById(intent.getLongExtra("DebtId", 0));
            }
        }

        System.out.println("Activité profile Debt");
    }


    private void configureViewModel() {
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(this);
        this.mMainViewModel = ViewModelProviders.of(this, mViewModelFactory).get(MainViewModel.class);
    }
}
