package org.tihlde;

import org.tihlde.DB.RegisterImp;
import org.tihlde.service.Broker;

import java.net.InetAddress;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by kanin on 04.05.14.
 */

/*
    Run two or more to get the Two Phase Commit effect.
 */

public class DBinit extends Thread {
    boolean failingTransfer;
    boolean poll;
    int id;

    public DBinit(int id, boolean failingTransfer, boolean poll) {
        this.failingTransfer = failingTransfer;
        this.poll = poll;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            String serverAddress = InetAddress.getLocalHost().toString();
            Registry registry = LocateRegistry.getRegistry(11000);
            Broker broker = (Broker) registry.lookup("Bank");
            System.out.println("Lookup on server: " + broker.getServerName() + " OK");
            broker.addRegistry(new RegisterImp(id, failingTransfer, poll));
            System.out.println("Connected");
        } catch (Exception re) {
            re.printStackTrace();
            System.out.println("Register failed to connect");
        }
    }
}