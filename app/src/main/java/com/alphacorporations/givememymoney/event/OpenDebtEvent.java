package com.alphacorporations.givememymoney.event;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alphacorporations.givememymoney.Constant;
import com.alphacorporations.givememymoney.View.ProfileDebtActivity;

public class OpenDebtEvent {
    public OpenDebtEvent(View v) {
        Intent intent = new Intent(v.getContext(), ProfileDebtActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong("DebtId", Constant.idDebt);
        intent.putExtras(bundle);
        v.getContext().startActivity(intent);
    }
}
