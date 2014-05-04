import org.junit.Before;
import org.junit.Test;
import org.tihlde.Client;
import org.tihlde.DTO.Transaction;
import org.tihlde.server.Server;
import org.tihlde.service.Broker;

import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

/**
 * Created by kanin on 04.05.14.
 */


public class TransactionTests {

    Broker broker;
    int id;

    @Before
    public void setUp() throws Exception {
        ArrayList<Client> clients = new ArrayList<Client>();
        Server server = new Server();
        server.start();

        Client register1 = new Client();
        Client register2 = new Client();
        Client register3 = new Client();
        Client register4 = new Client();

        clients.add(register1);
        clients.add(register2);
        clients.add(register3);
        clients.add(register4);

        for(Client c : clients) {
            c.start();
            c.join();
        }


        try {
            String serverAddress = InetAddress.getLocalHost().toString();
            Registry registry = LocateRegistry.getRegistry(12000);
            System.out.println("Locate OK");
            broker = (Broker) registry.lookup("Bank");
            System.out.println("Lookup on server: " + broker.getServerName() + " OK");
            System.out.println("Connected");
        } catch (RemoteException re) {
            re.printStackTrace();
            System.out.println("Register failed to connect");
        }
    }


    @Test
    public void successfulTransaction() throws Exception {
        Transaction newTrans = new Transaction();
        newTrans.setId(id);
        id++;
        newTrans.setAmount(1000);
        broker.makeTransaction(newTrans);
    }

    @Test
    public void failedTransaction() throws Exception() {

    }
}
