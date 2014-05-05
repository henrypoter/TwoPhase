package org.tihlde.service;

import org.tihlde.DB.Register;
import org.tihlde.DTO.Transaction;
import org.tihlde.Exceptions.NoTransactionFoundException;
import org.tihlde.Exceptions.PollingFailException;
import org.tihlde.Exceptions.TransactionFailedException;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by kanin on 03.05.14.
 */
public interface Broker extends Remote {

    String getServerName() throws RemoteException;

    void addRegistry(Register register) throws RemoteException;

    void makeTransaction(Transaction transaction) throws NoTransactionFoundException, RemoteException, TransactionFailedException, PollingFailException;

    void rollback(Transaction transaction) throws RemoteException;

    double getBalance() throws RemoteException;

    Transaction getTransaction(int id) throws RemoteException, NoTransactionFoundException;



}
