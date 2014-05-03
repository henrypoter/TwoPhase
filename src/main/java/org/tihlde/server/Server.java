package org.tihlde.server;

import org.tihlde.service.BrokerImp;

import java.net.UnknownHostException;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by kanin on 04.05.14.
 */
public class Server {
    public static void main(String[] args) throws Exception {

        final Registry registry = LocateRegistry.createRegistry(12000);
        BrokerImp broker = new BrokerImp();


        try {
            registry.bind("Bank", broker);
        } catch (Exception e) {
            System.out.println("Server failed to initialize");
            System.exit(0);
        }
        System.out.println("Server running...");

        while(true) {



        }
    }
}
