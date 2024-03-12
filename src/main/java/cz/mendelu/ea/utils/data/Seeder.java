package cz.mendelu.ea.utils.data;

import cz.mendelu.ea.domain.account.Account;
import cz.mendelu.ea.domain.account.AccountService;
import cz.mendelu.ea.domain.user.User;
import cz.mendelu.ea.domain.user.UserService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
public class Seeder {

    private final AccountService accountService;
    private final UserService userService;

    public Seeder(AccountService accountService, UserService userService) {
        this.accountService = accountService;
        this.userService = userService;
    }

    private boolean shouldSeedData(){
        return accountService.getAllAccounts().isEmpty();
    }
    @PostConstruct
    public void seedDefaultData() {
        User user1 = new User(1L, "Ivo", "ivo", new ArrayList<>());
        User user2 = new User(2L, "Marie", "mar777", new ArrayList<>());
        userService.createUser(user1);
        userService.createUser(user2);

        if (!shouldSeedData()){
            return;
        }
        Account account1 = new Account(1L, user1, 100.0);
        Account account2 = new Account(2L, user2, 200.0);
        accountService.createAccount(account1);
        accountService.createAccount(account2);

        user1.attachAccount(account2);
        account2.attachUser(user1);

        log.info("--- Default data seeded ---");
    }

}
