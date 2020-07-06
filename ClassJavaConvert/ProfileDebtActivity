package com.alphacorporations.givememymoney.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.alphacorporations.givememymoney.Constant;
import com.alphacorporations.givememymoney.R;
import com.alphacorporations.givememymoney.ViewModel.Injection;
import com.alphacorporations.givememymoney.ViewModel.ViewModelFactory;
import com.alphacorporations.givememymoney.model.Debt;
import com.alphacorporations.givememymoney.model.database.DebtDatabase;
import com.alphacorporations.givememymoney.model.database.dao.DebtDao;
import com.alphacorporations.givememymoney.model.repositories.ProfileDebtViewModel;

public class ProfileDebtActivity extends AppCompatActivity {

    final static int SELECT_PICTURE = 1;

    //UI COMPONENTS
    ImageView mImageView;
    EditText mNameDebt;
    EditText mDebtObject;
    EditText mDebtAmount;
    Button mSaveBtn;


    DebtDatabase mDatabase;
    DebtDao debtDao;
    ProfileDebtViewModel mProfileDebtViewModel;

    private Debt debt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_debt);

        mDatabase = DebtDatabase.getInstance(this);
        debtDao = mDatabase.debtDao();

        configureViewModel();
        populateList();
        initUI();

        mImageView.setOnClickListener(v -> selectAvatar());
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
        if(debt.getImg()==null) mImageView.setImageResource(R.drawable.ic_person_green);
        else mImageView.setImageURI(Uri.parse(debt.getImg()));
        mNameDebt.setText(debt.getName());
        mDebtObject.setText(debt.getObject());
        mDebtAmount.setText(String.valueOf(debt.getAmount()));
    }


    private void configureViewModel() {
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(this);
        this.mProfileDebtViewModel = ViewModelProviders.of(this, mViewModelFactory).get(ProfileDebtViewModel.class);
    }

    private void populateList() {
        final Observer<Debt> debtObserver = debtList -> {
            if (debtList != null) {
                debt = debtList;
                initDebtProfile();
            }
        };
        this.mProfileDebtViewModel.getDebtsById(Constant.idDebt).observe(this, debtObserver);
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
            mImageView.setImageURI(imageUri);
            debt.setImg(imageUri.toString());

        }
    }

    private void save() {
        debt.setName(mNameDebt.getText().toString());
        debt.setObject(mDebtObject.getText().toString());
        String amountstring = mDebtAmount.getText().toString();
        int amount = Integer.parseInt(amountstring);
        debt.setAmount(amount);

        this.mProfileDebtViewModel.updateDebt(debt);
        finish();
    }

}
