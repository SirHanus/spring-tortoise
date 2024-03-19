package cz.mendelu.ea.domain.transaction;

import cz.mendelu.ea.domain.account.Account;
import cz.mendelu.ea.utils.exceptions.InsufficientBalanceException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionService {

    private TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public Transaction processTransaction(Transaction transaction) {
        Account source = transaction.getSourceAccount();
        Account target = transaction.getTargetAccount();

        // check if source account has enough balance
        if (source.getBalance() < transaction.getAmount()) {
            throw new InsufficientBalanceException();
        }

        // process transaction in accounts
        source.processTransaction(transaction);
        target.processTransaction(transaction);

        // store transaction to have a complete history of all transactions
        repository.save(transaction);

        return transaction;
    }

    public Optional<Transaction> getById(UUID id){
        return repository.findById(id);
    }

}
