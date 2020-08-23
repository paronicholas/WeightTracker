package com.example.weighttracker.database.model;

public class TargetWeight {
    private int id;
    private int targetWeight;
    private int accountId;

    public TargetWeight() {
    }

    public TargetWeight(int targetWeight) {
        this.targetWeight = targetWeight;
    }

    public TargetWeight(int targetWeight, int accountId) {
        this.targetWeight = targetWeight;
        this.accountId = accountId;
    }

    public TargetWeight(int id, int targetWeight, int accountId) {
        this.id = id;
        this.targetWeight = targetWeight;
        this.accountId = accountId;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getTargetWeight() {
        return targetWeight;
    }

    public int getAccountId() {
        return accountId;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTargetWeight(int targetWeight) {
        this.targetWeight = targetWeight;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
}
