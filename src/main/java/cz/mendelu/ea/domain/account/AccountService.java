package cz.mendelu.ea.domain.account;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private List<Account> accounts = new ArrayList<>();
    private final AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<Account>();
        repository.findAll().forEach(accounts::add);
        return accounts;
    }

    public Account createAccount(Account account) {
        return repository.save(account);
    }

    public Optional<Account> getAccount(Long id) {
        return repository.findById(id);
    }

    public Account updateAccount(Long id, Account account) {

        account.setId(id);
        return repository.save(account);
    }

    public void deleteAccount(Long id) {
        repository.deleteById(id);
    }

}
