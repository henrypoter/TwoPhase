package org.tihlde.DB;

import org.tihlde.DTO.Transaction;
import org.tihlde.Exceptions.NoTransactionFoundException;
import org.tihlde.Exceptions.TransactionFailedException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Created by kanin on 03.05.14.
 */
public interface Register extends Remote {

    boolean ask() throws RemoteException;
    void rollback(Transaction transaction) throws RemoteException;
    ArrayList<Transaction> getAll() throws RemoteException;
    Transaction getById(int id) throws NoTransactionFoundException, RemoteException;
    Transaction makeTransaction(Transaction transaction) throws RemoteException, TransactionFailedException;
    Transaction getTransaction(int id) throws RemoteException, NoTransactionFoundException;
    double getBalance() throws RemoteException;
}