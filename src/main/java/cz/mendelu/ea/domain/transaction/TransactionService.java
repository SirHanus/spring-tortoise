package cz.mendelu.ea.domain.transaction;

import cz.mendelu.ea.domain.account.Account;
import cz.mendelu.ea.domain.account.AccountService;
import cz.mendelu.ea.utils.exceptions.InsufficientBalanceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    public Transaction processTransaction(Account source, Account target, Transaction transaction) {
        if (source.getBalance() < transaction.getAmount()) {
            throw new InsufficientBalanceException();
        }

        source.setBalance(source.getBalance() - transaction.getAmount());
        target.setBalance(target.getBalance() + transaction.getAmount());

        source.getTransactions().add(transaction);
        target.getTransactions().add(transaction);

        return transaction;
    }

}
