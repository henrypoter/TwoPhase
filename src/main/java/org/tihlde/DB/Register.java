package org.tihlde.DB;

import org.tihlde.DTO.Transaction;
import org.tihlde.Exceptions.NoTransactionFoundException;
import org.tihlde.service.Broker;

import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * Created by kanin on 04.05.14.
 */

public class Register extends UnicastRemoteObject implements RegistryInterface  {

    ArrayList<Transaction> bank = new ArrayList<Transaction>();

    public Register() throws RemoteException {
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
        return transaction;
    }

   public static void main(String[] args) throws Exception {
        try {
            String serverAddress = InetAddress.getLocalHost().toString();
            Registry registry = LocateRegistry.getRegistry(12000);
            Broker broker = (Broker) registry.lookup("Bank");
            broker.addRegistry(new Register());
            System.out.println("Connected");
        } catch (RemoteException re) {
            re.printStackTrace();
        }
    }
}