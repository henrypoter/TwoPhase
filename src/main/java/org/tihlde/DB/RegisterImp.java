package org.tihlde.DB;

import org.tihlde.DTO.Transaction;
import org.tihlde.Exceptions.NoTransactionFoundException;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * Created by kanin on 04.05.14.
 */

public class RegisterImp extends UnicastRemoteObject implements Register {

    ArrayList<Transaction> bank = new ArrayList<Transaction>();

    public RegisterImp() throws RemoteException {
    }

    @Override
    public boolean ask() {
        return true;
    }

    @Override
    public void rollback(Transaction transaction)  {
        bank.remove(transaction);
    }

    @Override
    public int checkVersion() throws RemoteException {
        return bank.get(bank.size()).getId() ;
    }

    @Override
    public ArrayList<Transaction> getAll() {
        return bank;
    }

    @Override
    public Transaction getById(int id) throws NoTransactionFoundException {
        for (Transaction t : bank) {
            if (t.getId() == id) {
                return t;
            }
        }
        throw new NoTransactionFoundException();
    }

    @Override
    public Transaction makeTransaction(Transaction transaction)  {
        bank.add(transaction);
        System.out.println("Transaction done.");
        return transaction;
    }
}