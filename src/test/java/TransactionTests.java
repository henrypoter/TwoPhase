import org.junit.Before;
import org.junit.Test;
import org.tihlde.DTO.Transaction;
import org.tihlde.service.BrokerImp;

/**
 * Created by kanin on 04.05.14.
 */


public class TransactionTests {

    @Before
    private void before() {

    }


    @Test
    private void successfulTransaction() {
        int id = 0;
        int version = 0;

        Transaction transaction = new Transaction();

        transaction.setBalance(0);
        transaction.setAmount(500);
        transaction.setId(id);
        transaction.setVersion(version);

        id++; version++;



    }


}
