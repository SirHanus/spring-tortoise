package cz.mendelu.ea.seminar1.domain.account;

import cz.mendelu.ea.seminar1.domain.transaction.Transaction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final List<Account> accounts = new ArrayList<>();


    public AccountService() {
        accounts.add(new Account(1L, "Mrkev", 100, new Transaction(1L, 1L, 1L, 1L)));
        accounts.add(new Account(2L, "Cibulka", 150));
        accounts.add(new Account(3L, "Paprika", 200));
        accounts.add(new Account(4L, "Rajčo", 200));
    }

    public List<Account> getAllAccounts() {
        return accounts;
    }

    public Optional<Account> getAccountById(Long id) {
        return accounts.stream().filter(a -> a.getId().equals(id)).findFirst();
    }

    public Account createAccount(Account account) {
        Long maxId = this.accounts.stream().map(Account::getId).max(Long::compareTo).orElse(0L);
        account.setId(maxId + 1);
        accounts.add(account);
        return account;

    }


    public Optional<Account> updateAccount(Long id, Account accountDetails) {
        Optional<Account> acc = getAccountById(id);

        acc.ifPresent(account -> {
            account.updateAccount(accountDetails);
        });
        return acc;
    }

    public void updateAccount(Account accountDetails, Transaction transaction) {
        Optional<Account> acc = getAccountById(accountDetails.getId());

        acc.ifPresent(account -> {
            account.updateAccount(accountDetails, transaction);
        });
    }

    public Optional<Account> deleteAccount(Long id) {
        Optional<Account> acc = getAccountById(id);
        acc.ifPresent(accounts::remove);
        return acc;
    }


}
