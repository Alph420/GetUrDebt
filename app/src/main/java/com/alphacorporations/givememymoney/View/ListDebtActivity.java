package com.alphacorporations.givememymoney.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alphacorporations.givememymoney.R;
import com.alphacorporations.givememymoney.ViewModel.Injection;
import com.alphacorporations.givememymoney.ViewModel.ViewModelFactory;
import com.alphacorporations.givememymoney.model.Debt;
import com.alphacorporations.givememymoney.model.MainViewModel;
import com.alphacorporations.givememymoney.model.database.DebtDatabase;
import com.alphacorporations.givememymoney.model.database.dao.DebtDao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ListDebtActivity extends AppCompatActivity implements DebtAdapter.DeleteTaskListener {

    // UI Components
    @BindView(R.id.add_debt)
    FloatingActionButton mFloatingActionButton;

    DebtDatabase mDatabase;
    DebtDao debtDao;
    MainViewModel mMainViewModel;

    private DebtAdapter adapter;
    private List<Debt> mDebtList = new ArrayList<>();



    @SuppressWarnings("NullableProblems")
    @NonNull
    private RecyclerView listDebts;

    @SuppressWarnings("NullableProblems")
    @NonNull
    private TextView lblNoTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDatabase = DebtDatabase.getInstance(this);
        debtDao = mDatabase.debtDao();

        this.configureViewModel();
        this.initList();

        setContentView(R.layout.activity_main);

        listDebts = findViewById(R.id.list_money);
        lblNoTasks = findViewById(R.id.lbl_no_task);
        listDebts.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        listDebts.setAdapter(adapter);

        listDebts = findViewById(R.id.list_money);
        lblNoTasks = findViewById(R.id.lbl_no_task);

        mFloatingActionButton = findViewById(R.id.add_debt);
        mFloatingActionButton.setOnClickListener(v -> addDebt());

    }


    private void initList() {
        populateList();
        adapter = new DebtAdapter(mDebtList, this, mDatabase);
    }

    @OnClick(R.id.add_debt)
    void addDebt() {
        ActivityCompat.startActivity(this, new Intent(this, AddDebtActivity.class), null);
    }

    private void populateList() {
        final Observer<List<Debt>> debtObserver = debtList -> {
            if (debtList != null) {
                mDebtList = debtList;
                updateTasks();
            }
        };
        this.mMainViewModel.getDebts().observe(this, debtObserver);
    }

    private void configureViewModel() {
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(this);
        this.mMainViewModel = ViewModelProviders.of(this, mViewModelFactory).get(MainViewModel.class);
    }

    @Override
    public void onDeleteDebt(Debt debt) {
        mDebtList.remove(debt);
        this.mMainViewModel.deleteDebt(debt.getId());
        updateTasks();
    }

    private void updateTasks() {
        if (mDebtList.size() == 0) {
            lblNoTasks.setVisibility(View.VISIBLE);
            listDebts.setVisibility(View.GONE);
        } else {
            lblNoTasks.setVisibility(View.GONE);
            listDebts.setVisibility(View.VISIBLE);

            adapter.updateTasks(mDebtList);
        }
    }
}