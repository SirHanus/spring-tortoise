package cz.mendelu.ea.domain.account;

import cz.mendelu.ea.domain.user.UserService;
import cz.mendelu.ea.utils.exceptions.NotFoundException;
import cz.mendelu.ea.utils.response.ArrayResponse;
import cz.mendelu.ea.utils.response.ObjectResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


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
    public ArrayResponse<AccountResponse> getAccounts() {
        return ArrayResponse.of(
                accountService.getAllAccounts(),
                AccountResponse::new
        );
    }

    @PostMapping(value = "", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @Valid
    public ObjectResponse<AccountResponse> createAccount(@RequestBody @Valid AccountRequest accountRequest) {
        Account account = new Account();
        accountRequest.toAccount(account, userService);

        accountService.createAccount(account);

        return ObjectResponse.of(account, AccountResponse::new);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @Valid
    public ObjectResponse<AccountResponse> getAccount(@PathVariable Long id) {
        Account account = accountService
                .getAccount(id)
                .orElseThrow(NotFoundException::new);
        return ObjectResponse.of(account, AccountResponse::new);
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Valid
    public ObjectResponse<AccountResponse> updateAccount(@PathVariable Long id, @RequestBody @Valid AccountRequest accountRequest) {
        Account account = accountService.getAccount(id)
                .orElseThrow(NotFoundException::new);
        accountRequest.toAccount(account, userService);

        accountService.updateAccount(id, account);

        return ObjectResponse.of(account, AccountResponse::new);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@PathVariable Long id) {
        accountService
                .getAccount(id)
                .ifPresent(account -> {
                    // remove account from user before deleting
                    // we will refactor this in the future to use cascades
                    account.getOwner().getAccounts().remove(account);
                    userService.updateUser(account.getOwner().getId(), account.getOwner());

                    accountService.deleteAccount(id);
                });
    }

}
