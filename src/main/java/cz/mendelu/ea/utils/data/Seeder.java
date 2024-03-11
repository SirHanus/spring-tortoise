package cz.mendelu.ea.utils.data;

import cz.mendelu.ea.domain.account.Account;
import cz.mendelu.ea.domain.account.AccountService;
import cz.mendelu.ea.domain.transaction.Transaction;
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

    @PostConstruct
    public void seedDefaultData() {

        User user1 = new User(0L, "Patrick", "patric2023");
        User user2 = new User(1L, "Samantha", "sammyBee");
        User user3 = new User(2L, "Alexander", "alexTheGreat");
        User user4 = new User(3L, "Isabella", "bellaStar");
        User user5 = new User(4L, "Michael", "mike123");
        User user6 = new User(5L, "Jessica", "jessieWaves");
        User user7 = new User(6L, "Daniel", "danTheMan");
        User user8 = new User(7L, "Emily", "emmy2023");
        User user9 = new User(8L, "James", "jimmyCricket");
        User user10 = new User(9L, "Sophia", "sophieSun");

        userService.addUser(user1);
        userService.addUser(user2);
        userService.addUser(user3);
        userService.addUser(user4);
        userService.addUser(user5);
        userService.addUser(user6);
        userService.addUser(user7);
        userService.addUser(user8);
        userService.addUser(user9);
        userService.addUser(user10);

// Create all accounts with the User objects
        Account account1 = new Account(0L, user1, 100.0);
        Account account2 = new Account(1L, user2, 200.0);
        Account account3 = new Account(2L, user3, 300.0);
        Account account4 = new Account(3L, user4, 400.0);
        Account account5 = new Account(4L, user5, 500.0);
        Account account6 = new Account(5L, user6, 600.0);
        Account account7 = new Account(6L, user7, 700.0);
        Account account8 = new Account(7L, user8, 800.0);
        Account account9 = new Account(8L, user9, 900.0);
        Account account10 = new Account(9L, user10, 1000.0);

// Add transactions using Account objects
        account1.addTransaction(new Transaction(0L, 50.0, account1, account2));
        account2.addTransaction(new Transaction(1L, 75.0, account2, account1));
        account2.addTransaction(new Transaction(2L, 30.0, account2, account3));
        account3.addTransaction(new Transaction(3L, 120.0, account3, account2));
        account3.addTransaction(new Transaction(4L, 90.0, account3, account4));
        account3.addTransaction(new Transaction(5L, 60.0, account3, account5));
        account5.addTransaction(new Transaction(6L, 250.0, account5, account1));
        account6.addTransaction(new Transaction(7L, 300.0, account6, account5));
        account6.addTransaction(new Transaction(8L, 200.0, account6, account8));
        account7.addTransaction(new Transaction(9L, 400.0, account7, account6));
        account8.addTransaction(new Transaction(10L, 500.0, account8, account7));
        account8.addTransaction(new Transaction(11L, 300.0, account8, account10));
        account10.addTransaction(new Transaction(12L, 700.0, account10, account9));

// Add all accounts to the account service using addAccount
        accountService.addAccount(account1);
        accountService.addAccount(account2);
        accountService.addAccount(account3);
        accountService.addAccount(account4);
        accountService.addAccount(account5);
        accountService.addAccount(account6);
        accountService.addAccount(account7);
        accountService.addAccount(account8);
        accountService.addAccount(account9);
        accountService.addAccount(account10);

        user2.addAccount(account1);
        user2.addAccount(account2);
        user3.addAccount(account3);
        user5.addAccount(account4);
        user5.addAccount(account5);
        user6.addAccount(account6);
        user8.addAccount(account7);
        user9.addAccount(account8);
        user10.addAccount(account9);
        user10.addAccount(account10);


        log.info("--- Default data seeded ---");
    }

}
