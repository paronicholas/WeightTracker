package com.example.weighttracker;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.weighttracker.database.helper.WeightTrackerDatabase;
import com.example.weighttracker.database.model.Account;

public class MainActivity extends AppCompatActivity {
    WeightTrackerDatabase db;
    private EditText username;
    private EditText password;
    private Button login;
    private TextWatcher buttonWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String user = username.getText().toString().trim();
            String pass = password.getText().toString().trim();
            login.setEnabled(!user.isEmpty() && !pass.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new WeightTrackerDatabase(getApplicationContext());
        username = findViewById(R.id.username_text);
        password = findViewById(R.id.password_text);
        login = findViewById(R.id.button_login);

        setButtonState();
        db.closeDb();
    }

    public void loginOrCreateAccount(View v) {
        String user = username.getText().toString();
        String pass = password.getText().toString();

        Account accountFromDB = db.getAccountByUsername(user);
        Account currentAccount = new Account();

        if (user.equals(accountFromDB.getUsername()) && !pass.equals(accountFromDB.getPassword())) {
            System.out.println("Wrong Credentials");
        } else {
            if (user.equals(accountFromDB.getUsername()) && pass.equals(accountFromDB.getPassword())) {
                currentAccount.setId(accountFromDB.getId());
                currentAccount.setUsername(accountFromDB.getUsername());
                currentAccount.setPassword(accountFromDB.getPassword());
            } else {
                currentAccount.setUsername(user);
                currentAccount.setPassword(pass);
                db.createAccount(currentAccount);
            }

            Intent intent = new Intent(this, Logbook.class);
            intent.putExtra("accountId", currentAccount.getId());
            startActivity(intent);
        }
    }

    private void setButtonState() {
        username.addTextChangedListener(buttonWatcher);
        password.addTextChangedListener(buttonWatcher);
    }
}