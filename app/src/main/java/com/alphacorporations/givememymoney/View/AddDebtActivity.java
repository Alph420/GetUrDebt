package com.alphacorporations.givememymoney.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alphacorporations.givememymoney.R;
import com.alphacorporations.givememymoney.ViewModel.Injection;
import com.alphacorporations.givememymoney.ViewModel.ViewModelFactory;
import com.alphacorporations.givememymoney.model.Debt;
import com.alphacorporations.givememymoney.model.MainViewModel;
import com.alphacorporations.givememymoney.model.database.DebtDatabase;
import com.alphacorporations.givememymoney.model.database.dao.DebtDao;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class AddDebtActivity extends AppCompatActivity {

    DebtDatabase mDatabase;
    DebtDao debtDao;
    MainViewModel mMainViewModel;
    Date date;


    private DebtAdapter adapter;
    private List<Debt> mDebtList = new ArrayList<>();

    final static int SELECT_PICTURE = 1;

    ImageView avatarDebt;
    TextView firstName;
    TextView lastName;
    TextView object;
    TextView amount;

    Button mButtonSave;

    Boolean canSave = false;
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
        amount.setText("0");

        mButtonSave = findViewById(R.id.save_debt);
        mButtonSave.setEnabled(false);
        mButtonSave.setTextColor(Color.RED);
        firstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) mButtonSave.setEnabled(true);
                mButtonSave.setTextColor(Color.WHITE);

                canSave =true;
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) mButtonSave.setEnabled(true);
                canSave =true;

            }
        });

        mButtonSave.setOnClickListener(v -> saving());

    }

    public void saving() {
        System.out.println("CLICK");
        long id = 0;
        int avatar = avatarDebt.getResources().getIdentifier("avatar", "strings", this.getPackageName());
        String name = firstName.getText() + " " + lastName.getText();

        String objectDebt;
        if (object.getText().equals("")) objectDebt = null;
        else objectDebt = object.getText().toString();

        int amountDebt = Integer.valueOf(amount.getText().toString());

        if (setDate) {
            Debt debt = new Debt(id, avatar, name, objectDebt, date.toString(), amountDebt);
            this.mMainViewModel.createDebt(debt);
            finish();

        } else {
            Debt debt = new Debt(id, avatar, name, objectDebt, null, amountDebt);
            this.mMainViewModel.createDebt(debt);
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
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                final Drawable imgSelect = new BitmapDrawable(getResources(), selectedImage);

                Glide.with(getApplicationContext())
                        .load(imgSelect)
                        .transform(new RoundedCorners(getResources().getDimensionPixelSize(R.dimen.corner_radius)))
                        .into(avatarDebt);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Une erreur s'est produite", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Vous n'avez pas choisi d'image", Toast.LENGTH_LONG).show();
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
            date = new GregorianCalendar(year, mon, day).getTime();
            setDate = true;
        });
        builderDatePicker.setNegativeButton(
                "Annuler",
                (dialog, id) -> dialog.cancel());
        builderDatePicker.show();
    }

    private void configureViewModel() {
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(this);
        this.mMainViewModel = ViewModelProviders.of(this, mViewModelFactory).get(MainViewModel.class);
    }

}
