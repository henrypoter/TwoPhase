package org.tihlde.server;


import org.tihlde.service.BrokerImp;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerInit extends Thread {

    @Override
    public void run() {
        try {
            final Registry registry = LocateRegistry.createRegistry(11000);
            BrokerImp broker = new BrokerImp("Bank");
            registry.bind(broker.getServerName(), broker);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ServerInit failed to initialize");
            System.exit(0);
        }
        System.out.println("ServerInit running...");

        boolean run = true;

        try {
            while (run) {

            }
        } catch (Exception e) {
            System.out.println("Wrong in while");
        }

      //registry.unbind("Bank");
        System.exit(0);

    }
}