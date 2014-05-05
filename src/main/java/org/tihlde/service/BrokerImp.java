package org.tihlde.service;

import org.tihlde.DB.Register;
import org.tihlde.DTO.Transaction;
import org.tihlde.Exceptions.NoTransactionFoundException;
import org.tihlde.Exceptions.PollingFailException;
import org.tihlde.Exceptions.TransactionFailedException;

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

    @Override
    public String getServerName() {
        return serverName;
    }

    @Override
    public void addRegistry(Register register) throws RemoteException {
        registers.add(register);
    }

    @Override
    public void makeTransaction(Transaction transaction) throws TransactionFailedException, PollingFailException, RemoteException {
        for(Register re : registers) {
            if(re.ask()) {
                throw new PollingFailException();
            }
        }

        try {
            for (Register r : registers) {
                r.makeTransaction(transaction);
            }
        } catch (TransactionFailedException tfe) {
            rollback(transaction);
            throw tfe;
        }
    }

    @Override
    public void rollback(Transaction transaction) throws RemoteException {
        for (Register r : registers) {
            r.rollback(transaction);
        }
    }

    @Override
    public double getBalance() throws RemoteException {
        return registers.get(0).getBalance();

    }

    @Override
    public Transaction getTransaction(int id) throws RemoteException {
        try {
            return registers.get(0).getTransaction(id);
        } catch (NoTransactionFoundException no) {
            return null;
        }
    }
}
