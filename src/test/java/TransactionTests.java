import org.junit.*;
import org.tihlde.DBinit;
import org.tihlde.DTO.Transaction;
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

    /**
     * Set up broker server and 4 fictional database servers.
     *
     * @throws Exception
     */
    @BeforeClass
    public static void setUpClass() throws Exception {
        ArrayList<DBinit> DBinits = new ArrayList<DBinit>();
        ServerInit serverInit = new ServerInit();
        DBinit register1 = new DBinit(1, false, false);
        DBinit register2 = new DBinit(2, false, false);
        DBinit register3 = new DBinit(3, false, false);
        DBinit register4 = new DBinit(4, false, false);

        DBinits.add(register1);
        DBinits.add(register2);
        DBinits.add(register3);
        DBinits.add(register4);

        serverInit.start();
        Thread.sleep(3000);
        for (DBinit c : DBinits) {
            c.start();
            c.join();
        }
    }

    @Before
    public void setUp() throws Exception {
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


    private Transaction addTransaction(int id, double amount) {
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setId(id);
        return transaction;
    }

    /**
     * Should add a transactions successfully to both databases.
     *
     * @throws Exception
     */
    @Test
    public void successful2Transaction() throws Exception {
        int id = 245;
        double amount = 1000;
        double balance = amount;

        // First transaction
        Transaction newTrans1 = addTransaction(id, amount);
        broker.makeTransaction(newTrans1);
        Transaction createdTrans1 = broker.getTransaction(id);
        Assert.assertNotNull(createdTrans1);
        Assert.assertEquals(id, createdTrans1.getId());
        Assert.assertEquals(amount, createdTrans1.getAmount(), 0.1);
        Assert.assertEquals(balance, createdTrans1.getBalance(), 0.1);

        // Second transaction
        id = 542;
        amount = 1500;
        balance += amount;

        Transaction newTrans2 = addTransaction(id, amount);
        broker.makeTransaction(newTrans2);
        Transaction createdTrans2 = broker.getTransaction(id);
        Assert.assertNotNull(createdTrans2);
        Assert.assertEquals(id, createdTrans2.getId());
        Assert.assertEquals(amount, createdTrans2.getAmount(), 0.1);
        Assert.assertEquals(balance, createdTrans2.getBalance(), 0.1);
    }
}
