package com.example.weighttracker.database.model;

public class DailyWeight {
    private int id;
    private String date;
    private int dailyWeight;
    private int plusMinus;
    private int accountId;

    public DailyWeight() {
    }

    public DailyWeight(String date, int dailyWeight, int accountId) {
        this.date = date;
        this.dailyWeight = dailyWeight;
        this.accountId = accountId;
    }

    public DailyWeight(String date, int dailyWeight, int plusMinus, int accountId) {
        this.date = date;
        this.dailyWeight = dailyWeight;
        this.plusMinus = plusMinus;
        this.accountId = accountId;
    }

    public DailyWeight(int id, String date, int dailyWeight, int plusMinus, int accountId) {
        this.id = id;
        this.date = date;
        this.dailyWeight = dailyWeight;
        this.plusMinus = plusMinus;
        this.accountId = accountId;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public int getDailyWeight() {
        return dailyWeight;
    }

    public int getPlusMinus() {
        return plusMinus;
    }

    public int getAccountId() {
        return accountId;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDailyWeight(int dailyWeight) {
        this.dailyWeight = dailyWeight;
    }

    public void setPlusMinus(int plusMinus) {
        this.plusMinus = plusMinus;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
}
