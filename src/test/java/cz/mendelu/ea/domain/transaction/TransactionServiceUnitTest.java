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
        /// given
        var source = new Account();
        source.setId(1L);
        source.setBalance(1000);

        var target = new Account();
        target.setId(2L);
        target.setBalance(200);

        Transaction transaction = new Transaction();
        transaction.setSourceAccount(source);
        transaction.setTargetAccount(target);
        transaction.setAmount(500);

        TransactionRepository repository = mock(TransactionRepository.class);
        TransactionService transactionService = new TransactionService(repository);

        // when
        transactionService.processTransaction(transaction);

        // then repository save was called
        verify(repository).save(transaction);

        // then
        assertThat(source.getBalance(), is(500.0));
        assertThat(target.getBalance(), is(700.0));
    }

    @Test
    public void testProcessTransaction_InsufficientBalance() {
        /// given
        var source = new Account();
        source.setId(1L);
        source.setBalance(1000);

        var target = new Account();
        target.setId(2L);
        target.setBalance(200);

        Transaction transaction = new Transaction();
        transaction.setSourceAccount(source);
        transaction.setTargetAccount(target);
        transaction.setAmount(1500);

        TransactionRepository repository = mock(TransactionRepository.class);
        TransactionService transactionService = new TransactionService(repository);

        // when called exception is thrown
        assertThrows(InsufficientBalanceException.class, () -> transactionService.processTransaction(transaction));

        // and nothing was saved
        assertThat(source.getBalance(), is(1000.0));
        assertThat(target.getBalance(), is(200.0));
        verify(repository, never()).save(any());
    }
}
