import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.tihlde.DTO.Transaction;
import org.tihlde.DBinit;
import org.tihlde.Exceptions.TransactionFailedException;
import org.tihlde.server.ServerInit;
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
    int id = 0;
    double balance = 0;

    /**
     * Set up broker server and 4 fictional database servers.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        ArrayList<DBinit> DBinits = new ArrayList<DBinit>();
        ServerInit serverInit = new ServerInit();
        DBinit register1 = new DBinit();
        DBinit register2 = new DBinit();
        DBinit register3 = new DBinit();
        DBinit register4 = new DBinit();

        DBinits.add(register1);
        DBinits.add(register2);
        //DBinits.add(register3);
        //DBinits.add(register4);

        serverInit.start();
        for(DBinit c : DBinits) {
            c.start();
            c.join();
        }


        try {
            String serverAddress = InetAddress.getLocalHost().toString();
            Registry registry = LocateRegistry.getRegistry(11000);
            System.out.println("Locate OK");
            broker = (Broker) registry.lookup("Bank");
            System.out.println("Lookup on serverInit: " + broker.getServerName() + " OK");
            System.out.println("Connected");
        } catch (RemoteException re) {
            re.printStackTrace();
            System.out.println("Client failed to connect");
        }
    }

    @After
    public void after() {
        id++;
    }


    /**
     * Should add a transactions successfully to both databases.
     * @throws Exception
     */
    @Test
    public void successfulTransaction() throws Exception {
        double amount = 1000;
        balance += amount;
        Transaction newTrans = new Transaction();
        newTrans.setId(id);
        newTrans.setAmount(amount);
        broker.makeTransaction(newTrans);
        Transaction createdTrans = broker.getTransaction(id);
        Assert.assertEquals(id, createdTrans.getId());
        Assert.assertEquals(amount, createdTrans.getAmount());
        Assert.assertEquals(balance, createdTrans.getBalance());

    }

    /**
     * Should try to add transaction but fail, and then rollback all servers.
     * @throws Exception
     */
    @Test(expected = TransactionFailedException.class)
    public void failedTransaction() throws Exception {
        double amount = 1500;
        Transaction newTrans = new Transaction();
        newTrans.setId(id);
        newTrans.setAmount(amount);
        broker.makeFailedTransaction(newTrans);
        Assert.assertNull(broker.getTransaction(id));
        double storedBalance = broker.getBalance();
        Assert.assertEquals(balance, storedBalance);
    }
}
