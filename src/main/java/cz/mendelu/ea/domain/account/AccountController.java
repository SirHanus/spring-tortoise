package cz.mendelu.ea.domain.account;

import cz.mendelu.ea.utils.exceptions.NotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("accounts")
@Validated
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(value = "", produces = "application/json")
    @Valid
    public List<Account> getAccounts() {
        return accountService.getAllAccounts();
    }

    @PostMapping(value = "", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @Valid
    public Account createAccount(@RequestBody @Valid Account account) {
        return accountService.createAccount(account);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @Valid
    public Account getAccount(@PathVariable Long id) {
        return accountService
                .getAccount(id)
                .orElseThrow(NotFoundException::new);
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Valid
    public Account updateAccount(@PathVariable Long id, @RequestBody @Valid Account account) {
        return accountService
                .updateAccount(id, account)
                .orElseThrow(NotFoundException::new);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
    }

}
