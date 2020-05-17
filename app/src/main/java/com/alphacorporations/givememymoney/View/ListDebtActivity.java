package com.alphacorporations.givememymoney.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toolbar;

import com.alphacorporations.givememymoney.Controler.MyDebtRecyclerViewAdapter;
import com.alphacorporations.givememymoney.DI.DI;
import com.alphacorporations.givememymoney.R;
import com.alphacorporations.givememymoney.events.DeleteDebtEvent;
import com.alphacorporations.givememymoney.model.Debt;
import com.alphacorporations.givememymoney.service.DebtApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;

public class ListDebtActivity extends AppCompatActivity {

    // UI Components
    @BindView(R.id.container)
    ViewPager mViewPager;


    DebtApiService mApiService;
    RecyclerView mRecyclerView;

    private List<Debt> mDebtList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mApiService = DI.getDebtApiService();

        mRecyclerView = findViewById(R.id.list_money);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(new MyDebtRecyclerViewAdapter(mDebtList));

        initList();

    }


    private void initList() {
        mRecyclerView.setAdapter(new MyDebtRecyclerViewAdapter(mApiService.getDetbs()));
    }

    //region RegionOverride
    @Override
    public void onResume() {
        super.onResume();
        System.out.println("Resume");
        initList();
    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("Start");
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        System.out.println("Stop");
        EventBus.getDefault().unregister(this);
    }
    //endregion

    @Subscribe
    public void onDeleteDebtEvent(DeleteDebtEvent event) {
        mApiService.deleteDebt(event.debt);
        initList();
    }
}
