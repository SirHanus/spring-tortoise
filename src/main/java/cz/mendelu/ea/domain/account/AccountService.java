package cz.mendelu.ea.domain.account;

import cz.mendelu.ea.domain.transaction.Transaction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private List<Account> accounts = new ArrayList<>();

    public AccountService() {
        Account account0 = new Account(0L, "Jakub Novák", 100.0);
        account0.addTransaction(new Transaction(0L, 200.0, 0L, 1L));
        account0.addTransaction(new Transaction(1L, 150.0, 0L, 2L));
        accounts.add(account0);

        Account account1 = new Account(1L, "Eliška Svobodová", 200.0);
        account1.addTransaction(new Transaction(2L, 250.0, 1L, 0L));
        accounts.add(account1);

        Account account2 = new Account(2L, "Tomáš Dvořák", 300.0);
        account2.addTransaction(new Transaction(3L, 100.0, 2L, 1L));
        account2.addTransaction(new Transaction(4L, 50.0, 2L, 3L));
        account2.addTransaction(new Transaction(5L, 75.0, 2L, 4L));
        accounts.add(account2);

        Account account3 = new Account(3L, "Anna Horáková", 400.0);
        accounts.add(account3);

        Account account4 = new Account(4L, "Martin Procházka", 500.0);
        account4.addTransaction(new Transaction(6L, 125.0, 4L, 5L));
        account4.addTransaction(new Transaction(7L, 300.0, 4L, 6L));
        accounts.add(account4);

        Account account5 = new Account(5L, "Lucie Němcová", 600.0);
        accounts.add(account5);

        Account account6 = new Account(6L, "Petr Kříž", 700.0);
        account6.addTransaction(new Transaction(8L, 200.0, 6L, 7L));
        accounts.add(account6);

        Account account7 = new Account(7L, "Klára Musilová", 800.0);
        account7.addTransaction(new Transaction(9L, 50.0, 7L, 8L));
        account7.addTransaction(new Transaction(10L, 150.0, 7L, 9L));
        accounts.add(account7);

        Account account8 = new Account(8L, "Radek Černý", 900.0);
        accounts.add(account8);

        Account account9 = new Account(9L, "Michaela Veselá", 1000.0);
        account9.addTransaction(new Transaction(11L, 500.0, 9L, 0L));
        accounts.add(account9);

    }

    public List<Account> getAllAccounts() {
        return accounts;
    }

    public Account createAccount(Account account) {
        // we will try a little bit of stream API here, it can be used instead of for loops to make the code more readable
        Long maxId = accounts.stream()
                .map(Account::getId)
                .max(Long::compareTo)
                .orElse(0L);
        account.setId(maxId + 1);
        accounts.add(account);
        return account;
    }

    public Optional<Account> getAccount(Long id) {
        return accounts.stream()
                .filter(account -> account.getId().equals(id))
                .findFirst();
    }

    public Optional<Account> updateAccount(Long id, Account account) {
        Optional<Account> accountOptional = getAccount(id);
        accountOptional.ifPresent(a -> {
            a.setId(id);
            a.setOwner(account.getOwner());
            a.setBalance(account.getBalance());
        });
        return accountOptional;
    }

    public void deleteAccount(Long id) {
        accounts.removeIf(account -> account.getId().equals(id));
    }

}
