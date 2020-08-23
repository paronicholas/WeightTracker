package com.example.weighttracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.weighttracker.database.helper.WeightTrackerDatabase;
import com.example.weighttracker.database.model.Account;

import java.util.ArrayList;
import java.util.List;

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
        boolean newAccount = true;
        List<Account> accounts = db.getAllAccounts();
        Account currentAccount = new Account();

        for (Account account : accounts) {
            if (account.getUsername().equals(username.getText().toString()) && account.getPassword().equals(password.getText().toString())) {
                newAccount = false;
                currentAccount = account;
            }
        }

        if (newAccount) {
            currentAccount.setUsername(username.getText().toString());
            currentAccount.setPassword(password.getText().toString());
            db.createAccount(currentAccount);
        }

        System.out.println("account id: " + currentAccount.getId());
        Intent intent = new Intent(this, Logbook.class);
        intent.putExtra("accountId", currentAccount.getId());
        startActivity(intent);
    }

    private void setButtonState() {
        username.addTextChangedListener(buttonWatcher);
        password.addTextChangedListener(buttonWatcher);
    }
}