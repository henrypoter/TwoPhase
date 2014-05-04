package org.tihlde.server;

import org.tihlde.service.BrokerImp;

import java.net.UnknownHostException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by kanin on 04.05.14.
 */
public class Server {
    public static void main(String[] args) throws RemoteException {

        final Registry registry = LocateRegistry.createRegistry(12000);
        BrokerImp broker = new BrokerImp("Bank");

        try {
            registry.bind(broker.getServerName(), broker);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Server failed to initialize");
            System.exit(0);
        }
        System.out.println("Server running...");

        boolean run = true;

        while (run) {



        }

      //registry.unbind("Bank");
        System.exit(0);

    }
}