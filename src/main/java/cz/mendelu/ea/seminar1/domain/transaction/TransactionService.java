package cz.mendelu.ea.seminar1.domain.transaction;

import cz.mendelu.ea.seminar1.domain.account.Account;
import cz.mendelu.ea.seminar1.domain.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TransactionService {

    private final AccountService accountService;

    @Autowired
    public TransactionService(AccountService accountService){
        this.accountService = accountService;
    }


    public List<Transaction> getAllTransactions() {
        Set<Transaction> uniqueTransactions = new HashSet<>();
        accountService.getAllAccounts().forEach(account -> uniqueTransactions.addAll(account.getTransactions()));
        return (new ArrayList<>(uniqueTransactions));

    }
    public Transaction processTransaction(Transaction transaction, Account source, Account target) {



        // Update account balances
        source.setBalance(source.getBalance() - transaction.getAmount());
        target.setBalance(target.getBalance() + transaction.getAmount());

        // Save updated accounts
        accountService.updateAccount(source, transaction);
        accountService.updateAccount(target, transaction);

        return transaction;
    }
}


