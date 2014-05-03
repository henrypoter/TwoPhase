package org.tihlde.server;

import org.tihlde.DTO.Transaction;

import java.rmi.Remote;
import java.util.ArrayList;

/**
 * Created by kanin on 03.05.14.
 */
public interface Server extends Remote {

    boolean ask();
    Transaction rollback();
    boolean checkVersion();
    ArrayList<Transaction> getAll();
    Transaction getById(int id);
    Transaction makeTransaction(double amount);


}
