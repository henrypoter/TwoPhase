package org.tihlde.DB;

import org.tihlde.DTO.Transaction;
import org.tihlde.Exceptions.NoTransactionFoundException;
import org.tihlde.Exceptions.TransactionFailedException;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * Created by kanin on 04.05.14.
 */

public class RegisterImp extends UnicastRemoteObject implements Register {

    ArrayList<Transaction> bank = new ArrayList<Transaction>();
    Transaction prevTransaction = null;
    boolean failing;
    boolean poll;
    int nodeId;

    public RegisterImp(int id, boolean failing, boolean poll) throws RemoteException {
        this.failing = failing;
        this.poll = poll;
        this.nodeId = id;
    }

    @Override
    public boolean ask() {
        return poll;
    }

    @Override
    public void rollback(Transaction transaction)  {
        System.out.println("Rollback done.");
        bank.remove(transaction);
    }

    @Override
    public ArrayList<Transaction> getAll() {
        return bank;
    }

    @Override
    public Transaction getById(int id) {
        for (Transaction t : bank) {
            if (t.getId() == id) {
                return t;
            }
        }
        return null;
    }

    @Override
    public Transaction makeTransaction(Transaction transaction) throws TransactionFailedException {
        // Failing trasaction for testing.
        if(failing) {
            throw new TransactionFailedException("Transaction failed in node " + nodeId);
        }
        double newBalance;
        if (prevTransaction != null) {
            newBalance = prevTransaction.getBalance() + transaction.getAmount();
            transaction.setBalance(newBalance);
        } else {
            transaction.setBalance(transaction.getAmount());
        }

        bank.add(transaction);
        prevTransaction = transaction;
        System.out.println("Transaction done.");
        return transaction;
    }

    @Override
    public Transaction getTransaction(int id) throws NoTransactionFoundException {
        for(Transaction t : bank) {
            if(t.getId() == id) {
                return t;
            }
        }
        throw new NoTransactionFoundException();
    }

    @Override
    public double getBalance() {
        return bank.get(bank.size() - 1).getBalance();
    }
}