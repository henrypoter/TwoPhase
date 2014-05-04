package org.tihlde.service;

import org.tihlde.DB.Register;
import org.tihlde.DTO.Transaction;
import org.tihlde.Exceptions.NoTransactionFoundException;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * Created by kanin on 04.05.14.
 */
public class BrokerImp extends UnicastRemoteObject implements Broker {

    private String serverName;
    private ArrayList<Register> registers = new ArrayList<Register>();

    public BrokerImp(String serverName) throws RemoteException {
        this.serverName = serverName;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    @Override
    public void addRegistry(Register register) throws RemoteException {
        registers.add(register);
    }

    @Override
    public ArrayList<Register> getRegisters() throws RemoteException {
        return registers;
    }
/*
    @Override
    public void makeTransaction(Transaction transaction) throws NoTransactionFoundException, RemoteException {

        for (Register r : registers) {
            r.makeTransaction(transaction);
        }
        try {
            checkVersion(transaction);
        } catch (Exception e) {

        }
    }

    @Override
    public boolean checkVersion(Transaction transaction) throws NoTransactionFoundException, RemoteException {
        for (Register r : registers) {
            if (r.getById(transaction.getId()).getVersion() != transaction.getVersion()) {
                rollback(transaction);
                System.out.println("Rollback completed");
                return false;
            }
        }
        return true;
    }

    @Override
    public void rollback(Transaction transaction) throws RemoteException {
        for (Register r : registers) {
            r.rollback(transaction);
        }
    }*/
}
