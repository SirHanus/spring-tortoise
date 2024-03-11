package cz.mendelu.ea.domain.account;

import cz.mendelu.ea.domain.user.User;
import cz.mendelu.ea.domain.user.UserService;
import cz.mendelu.ea.utils.exceptions.NotFoundException;
import cz.mendelu.ea.utils.reponse.ArrayResponse;
import cz.mendelu.ea.utils.reponse.ObjectResponse;
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
    private final UserService userService;

    @Autowired
    public AccountController(AccountService accountService, UserService userService) {
        this.accountService = accountService;
        this.userService = userService;
    }

    @GetMapping(value = "", produces = "application/json")
    @Valid
    public ArrayResponse<Account> getAccounts() {
        return new ArrayResponse<>(accountService.getAllAccounts(), accountService.getAllAccounts().size());
    }

    @PostMapping(value = "", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @Valid
    public ObjectResponse<AccountResponse> createAccount(@RequestBody @Valid AccountRequest accountRequest) {
        Account account = new Account();
        accountRequest.toAccount(account, userService, accountService);
        accountService.addAccount(account);
        return new ObjectResponse<>(new AccountResponse(account));
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @Valid
    public ObjectResponse<AccountResponse> getAccount(@PathVariable Long id) {
        return new ObjectResponse<>(new AccountResponse(accountService
                .getAccount(id)
                .orElseThrow(NotFoundException::new)));
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Valid
    public ObjectResponse<AccountResponse> updateAccount(@PathVariable Long id, @RequestBody @Valid AccountRequest accountRequest) {
        Account account = new Account();
        accountRequest.toAccount(account, userService, accountService);
        return new ObjectResponse<>(new AccountResponse(accountService
                .updateAccount(id, account)
                .orElseThrow(NotFoundException::new)));
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
    }

}
