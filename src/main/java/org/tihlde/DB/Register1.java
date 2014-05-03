package org.tihlde.DB;

import org.tihlde.DTO.Transaction;
import org.tihlde.server.Server;

import java.util.ArrayList;

/**
 * Created by kanin on 04.05.14.
 */
public class Register1 implements Server {


    @Override
    public boolean ask() {
        return false;
    }

    @Override
    public Transaction rollback() {
        return null;
    }

    @Override
    public boolean checkVersion() {
        return false;
    }

    @Override
    public ArrayList<Transaction> getAll() {
        return null;
    }

    @Override
    public Transaction getById(int id) {
        return null;
    }

    @Override
    public Transaction makeTransaction(double amount) {
        return null;
    }
}
