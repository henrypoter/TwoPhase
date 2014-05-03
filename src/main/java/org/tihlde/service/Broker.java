package org.tihlde.service;

import org.tihlde.DB.Register;
import org.tihlde.DTO.Transaction;
import org.tihlde.Exceptions.NoTransactionFoundException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

/**
 * Created by kanin on 03.05.14.
 */
public interface Broker extends Remote {

    void addRegistry(Register register);
    ArrayList<Register> getRegisters();
    void makeTransaction(Transaction transaction) throws NoTransactionFoundException;
    boolean checkVersion(Transaction transaction) throws NoTransactionFoundException;
    void rollback(Transaction transaction);

}
