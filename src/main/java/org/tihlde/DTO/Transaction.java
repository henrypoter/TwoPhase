package org.tihlde.DTO;

import java.io.Serializable;

/**
 * Created by kanin on 03.05.14.
 */
public class Transaction implements Serializable {

    private int id;
    private double amount;
    private double balance;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}