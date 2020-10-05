package com.example.weighttracker.database.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.weighttracker.database.model.Account;
import com.example.weighttracker.database.model.DailyWeight;
import com.example.weighttracker.database.model.TargetWeight;

import java.util.ArrayList;
import java.util.List;

public class WeightTrackerDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "weight_tracker.db";
    private static final int VERSION = 9;

    private static final String LOG = WeightTrackerDatabase.class.getName();

    public WeightTrackerDatabase(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    // Table names:
    private static final String TABLE_ACCOUNTS = "accounts";
    private static final String TABLE_DAILY_WEIGHTS = "daily_weights";
    private static final String TABLE_TARGET_WEIGHT = "target_weight";

    // Common column names:
    private static final String KEY_ID = "_id";

    // Accounts column names:
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";

    // Daily weight column names:
    private static final String KEY_DATE = "date";
    private static final String KEY_DAILY_WEIGHT = "daily_weight";

    // Target weight column names:
    private static final String KEY_TARGET_WEIGHT = "target_weight";

    // Linking table column names:
    private static final String KEY_ACCOUNT_ID = "account_id";

    // Create table statements:
    private static final String CREATE_TABLE_ACCOUNTS = String.format(
            "CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT);", TABLE_ACCOUNTS, KEY_ID, KEY_USERNAME, KEY_PASSWORD
    );
    private static final String CREATE_TABLE_DAILY_WEIGHTS = String.format(
            "CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s INT);", TABLE_DAILY_WEIGHTS, KEY_ID, KEY_DATE, KEY_DAILY_WEIGHT, KEY_ACCOUNT_ID
    );
    private static final String CREATE_TABLE_TARGET_WEIGHTS = String.format(
            "CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s INT);", TABLE_TARGET_WEIGHT, KEY_ID, KEY_TARGET_WEIGHT, KEY_ACCOUNT_ID
    );

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tables:
        db.execSQL(CREATE_TABLE_ACCOUNTS);
        db.execSQL(CREATE_TABLE_DAILY_WEIGHTS);
        db.execSQL(CREATE_TABLE_TARGET_WEIGHTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(String.format("DROP TABLE IF EXISTS %s ", TABLE_ACCOUNTS));
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", TABLE_DAILY_WEIGHTS));
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", TABLE_TARGET_WEIGHT));

        onCreate(db);
    }

    public void closeDb() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    /**
     * Account Table Methods
     */
    public long createAccount(Account account) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, account.getUsername());
        values.put(KEY_PASSWORD, account.getPassword());

        long accountId = db.insert(TABLE_ACCOUNTS, null, values);
        return accountId;
    }

    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        String selectQuery = String.format("SELECT * FROM %s", TABLE_ACCOUNTS);

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Account account = new Account();

                account.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                account.setUsername(c.getString(c.getColumnIndex(KEY_USERNAME)));
                account.setPassword(c.getString(c.getColumnIndex(KEY_PASSWORD)));

                accounts.add(account);
            } while (c.moveToNext());
        }

        return accounts;
    }

    public Account getAccountByUsername(String username) {
        Account account = new Account();
        String selectQuery = String.format("SELECT * FROM %S WHERE username = \"%s\";", TABLE_ACCOUNTS, username);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            c.moveToFirst();
            account.setId(c.getInt(c.getColumnIndex(KEY_ID)));
            account.setUsername(c.getString(c.getColumnIndex(KEY_USERNAME)));
            account.setPassword(c.getString(c.getColumnIndex(KEY_PASSWORD)));
        }

        return account;
    }

    /**
     * Daily Weight Table Methods
     */
    public long createDailyWeight(DailyWeight dailyWeight) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        System.out.println("Date to add: " + dailyWeight.getDate());
        values.put(KEY_DATE, dailyWeight.getDate());
        values.put(KEY_DAILY_WEIGHT, dailyWeight.getDailyWeight());
        values.put(KEY_ACCOUNT_ID, dailyWeight.getAccountId());
        System.out.println("Date value to add: " + values.get(KEY_DATE));

        long accountId = db.insert(TABLE_DAILY_WEIGHTS, null, values);
        return accountId;
    }

    public List<DailyWeight> getDailyWeights() {
        List<DailyWeight> dailyWeights = new ArrayList<>();
        String selectQuery = String.format("SELECT * FROM %s", TABLE_DAILY_WEIGHTS);

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                DailyWeight dailyWeight = new DailyWeight();
                dailyWeight.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                dailyWeight.setDate(String.valueOf(c.getInt(c.getColumnIndex(KEY_DATE))));
                dailyWeight.setDailyWeight(c.getInt(c.getColumnIndex(KEY_DAILY_WEIGHT)));
                dailyWeight.setAccountId(c.getInt(c.getColumnIndex(KEY_ACCOUNT_ID)));

                dailyWeights.add(dailyWeight);
            } while (c.moveToNext());
        }

        return dailyWeights;
    }

    public List<DailyWeight> getDailyWeightByAccountId(int accountId) {
        List<DailyWeight> dailyWeights = new ArrayList<>();
        String selectQuery = String.format("SELECT * FROM %S WHERE account_id = '%s';", TABLE_DAILY_WEIGHTS, accountId);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            c.moveToFirst();

            do {
                DailyWeight dailyWeight = new DailyWeight();
                dailyWeight.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                dailyWeight.setDailyWeight(c.getInt(c.getColumnIndex(KEY_DAILY_WEIGHT)));
                dailyWeight.setDate(c.getString(c.getColumnIndex(KEY_DATE)));
                dailyWeight.setAccountId(c.getInt(c.getColumnIndex(KEY_ACCOUNT_ID)));

                dailyWeights.add(dailyWeight);
            } while (c.moveToNext());
        }

        return dailyWeights;
    }

    /**
     * Profile Table Methods
     */
    public long createTargetWeight(TargetWeight targetWeight) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_TARGET_WEIGHT, targetWeight.getTargetWeight());
        values.put(KEY_ACCOUNT_ID, targetWeight.getAccountId());

        long accountId = db.insert(TABLE_TARGET_WEIGHT, null, values);
        return accountId;
    }

    public List<TargetWeight> getTargetWeights() {
        List<TargetWeight> targetWeights = new ArrayList<>();
        String selectQuery = String.format("SELECT * FROM %s", TABLE_TARGET_WEIGHT);

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                TargetWeight targetWeight = new TargetWeight();

                targetWeight.setId(c.getInt(c.getColumnIndex(KEY_ID)));
                targetWeight.setTargetWeight(c.getColumnIndex(KEY_TARGET_WEIGHT));

                targetWeights.add(targetWeight);
            } while (c.moveToNext());
        }

        return targetWeights;
    }

    public TargetWeight getTargetWeightByAccountId(int accountId) {
        TargetWeight targetWeight = new TargetWeight();
        String selectQuery = String.format("SELECT * FROM %s WHERE account_id = \"%s\";", TABLE_TARGET_WEIGHT, accountId);

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            c.moveToFirst();
            targetWeight.setId(c.getInt(c.getColumnIndex(KEY_ID)));
            targetWeight.setTargetWeight(c.getInt(c.getColumnIndex(KEY_TARGET_WEIGHT)));
            targetWeight.setAccountId(c.getInt(c.getColumnIndex(KEY_ACCOUNT_ID)));
        }

        return targetWeight;
    }

    public int updateTargetWeight(TargetWeight targetWeight) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, targetWeight.getId());
        values.put(KEY_TARGET_WEIGHT, targetWeight.getTargetWeight());
        values.put(KEY_ACCOUNT_ID, targetWeight.getAccountId());
        return db.update(TABLE_TARGET_WEIGHT,
                values,
                KEY_ID + " = ?",
                new String[] {String.valueOf(targetWeight.getId())}
                );
    }
}
