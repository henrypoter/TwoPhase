package org.tihlde;

import org.tihlde.DB.RegisterImp;
import org.tihlde.service.Broker;

import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by kanin on 04.05.14.
 */

/*
    Run two or more to get the Two Phase Commit effect.
 */

public class Client {

    public static void main(String[] args) throws Exception {
        try {
            String serverAddress = InetAddress.getLocalHost().toString();
            Registry registry = LocateRegistry.getRegistry(12000);
            System.out.println("Locate OK");
            Broker broker = (Broker) registry.lookup("Bank");
            System.out.println("Lookup on server: " + broker.getServerName() + " OK");
            broker.addRegistry(new RegisterImp());
            System.out.println("Connected");
        } catch (RemoteException re) {
            re.printStackTrace();
            System.out.println("Register failed to connect");
        }
    }
}