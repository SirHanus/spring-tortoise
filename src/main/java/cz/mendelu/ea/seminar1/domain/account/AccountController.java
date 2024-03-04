package cz.mendelu.ea.seminar1.domain.account;

import cz.mendelu.ea.seminar1.utils.exceptions.NotFound;
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
    public AccountController(AccountService accountService){
     this.accountService = accountService;
    }

    @GetMapping(value="", produces = "application/json")
    @Valid
    public List<Account> getAccounts(){
        return this.accountService.getAllAccounts();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public Account getAccount(@PathVariable Long id){
        return accountService.getAccountById(id).orElseThrow(NotFound::new);
    }

    @PostMapping(value="", produces="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @Valid
    public Account createAccount(@RequestBody @Valid Account account){
        return accountService.createAccount(account);
    }

}
