package org.tihlde.service;

import org.tihlde.DB.Register;
import org.tihlde.DB.RegisterImp;
import org.tihlde.DTO.Transaction;
import org.tihlde.Exceptions.NoTransactionFoundException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Created by kanin on 03.05.14.
 */
public interface Broker extends Remote {

    String getServerName() throws RemoteException;

    void addRegistry(Register register) throws RemoteException;

    ArrayList<Register> getRegisters() throws RemoteException;

    void makeTransaction(Transaction transaction) throws NoTransactionFoundException, RemoteException;

    boolean checkVersion(Transaction transaction) throws NoTransactionFoundException, RemoteException;

    void rollback(Transaction transaction) throws RemoteException;

}
