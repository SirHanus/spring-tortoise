package cz.mendelu.ea.domain.transaction;

import cz.mendelu.ea.domain.account.Account;
import cz.mendelu.ea.utils.exceptions.InsufficientBalanceException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
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


        return transactionRepository.save(transaction);
    }



}
