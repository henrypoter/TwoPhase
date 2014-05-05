import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.tihlde.DBinit;
import org.tihlde.DTO.Transaction;
import org.tihlde.Exceptions.PollingFailException;
import org.tihlde.server.ServerInit;
import org.tihlde.service.Broker;

import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

/**
 * Created by bjorn
 */

public class TransactionPollFailTest {

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
        DBinit register3 = new DBinit(3, false, true);
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
     * Should try to add transaction but fail, and then rollback all servers.
     *
     * @throws org.tihlde.Exceptions.TransactionFailedException
     * @throws Exception
     */
    @Test(expected = PollingFailException.class)
    public void failedTransaction() throws Exception {

        int id = 4324;
        double amount = 1500;

        Transaction newTrans = addTransaction(id, amount);
        broker.makeTransaction(newTrans);
    }
}
