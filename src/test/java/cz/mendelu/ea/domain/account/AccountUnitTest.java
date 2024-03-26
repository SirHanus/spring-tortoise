package cz.mendelu.ea.domain.account;

import cz.mendelu.ea.domain.transaction.Transaction;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AccountUnitTest {

    @Test
    public void testGetSumOfAllTransactions(){
        // given
        Account account = new Account();

        Transaction transaction1 = new Transaction();
        transaction1.setAmount(1000);
        Transaction transaction2 = new Transaction();
        transaction2.setAmount(200);
        Transaction transaction3 = new Transaction();
        transaction3.setAmount(300);


        account.getOutgoingTransactions().add(transaction1);
        account.getIncomingTransactions().add(transaction2);
        account.getIncomingTransactions().add(transaction3);

        double sum = account.getSumOfAllTransactions();

        assertThat(sum, is(-500.0));

        //when


        //then
    }

}
