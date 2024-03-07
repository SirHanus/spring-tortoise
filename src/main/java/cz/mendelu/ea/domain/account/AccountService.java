package cz.mendelu.ea.domain.account;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private List<Account> accounts = new ArrayList<>();

    public AccountService() {
        accounts.add(new Account(1L, "Ivo Pisarovic", 100.0));
        accounts.add(new Account(2L, "Jane Doe", 200.0));
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
