package cz.mendelu.ea.domain.transaction;

import cz.mendelu.ea.domain.account.Account;
import cz.mendelu.ea.utils.exceptions.InsufficientBalanceException;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class TransactionServiceUnitTest {


    @Test
    public void testProcessTransaction() {
        //given
        Account source = new Account();
        source.setId(1L);
        source.setBalance(1000);
        Account target = new Account();
        target.setId(2L);
        target.setBalance(500);


        Transaction transaction = new Transaction();
        transaction.setSourceAccount(source);
        transaction.setTargetAccount(target);
        transaction.setAmount(500);

        //simulate DB - mocking
        var transactionRepository = mock(TransactionRepository.class);
        TransactionService transactionService = new TransactionService(transactionRepository);
        //when
        transactionService.processTransaction(transaction);


        //then

        verify(transactionRepository).save(transaction); // ověřím že se zavolala metoda save na metodě transactonRepository - ergo - checkuju že jsem se pokusil uložit do DB

        assertThat(source.getBalance(), is(500.0));
        assertThat(target.getBalance(), is(1000.0));
    }

    @Test
    public void testProcessTransaction_InsufficientBalance() {
        //given
        Account source = new Account();
        source.setId(1L);
        source.setBalance(1000);
        Account target = new Account();
        target.setId(2L);
        target.setBalance(500);


        Transaction transaction = new Transaction();
        transaction.setSourceAccount(source);
        transaction.setTargetAccount(target);
        transaction.setAmount(1500);

        //simulate DB - mocking
        var transactionRepository = mock(TransactionRepository.class);
        TransactionService transactionService = new TransactionService(transactionRepository);
        //when
        assertThrows(
                InsufficientBalanceException.class, ()->transactionService.processTransaction(transaction)
        );
//        transactionService.processTransaction(transaction);


        //then

        verify(transactionRepository, never()).save(transaction); // ověřím že se zavolala metoda save na metodě transactonRepository - ergo - checkuju že jsem se pokusil uložit do DB

        assertThat(source.getBalance(), is(1000.0));
        assertThat(target.getBalance(), is(500.0));

    }
}
