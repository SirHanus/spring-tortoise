package cz.mendelu.ea.utils.data;

import cz.mendelu.ea.domain.account.Account;
import cz.mendelu.ea.domain.account.AccountService;
import cz.mendelu.ea.domain.loan.Loan;
import cz.mendelu.ea.domain.loan.LoanService;
import cz.mendelu.ea.domain.user.User;
import cz.mendelu.ea.domain.user.UserService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Slf4j
public class Seeder {

    private final AccountService accountService;
    private final UserService userService;

    private final LoanService loanService;

    public Seeder(AccountService accountService, UserService userService, LoanService loanService) {
        this.accountService = accountService;
        this.userService = userService;
        this.loanService = loanService;
    }

    private boolean shouldSeedData() {
        return accountService.getAllAccounts().isEmpty() || userService.getAllUsers().isEmpty();
    }

    @PostConstruct
    public void seedDefaultData() {
        if (!shouldSeedData()) {
            log.info("--- Default data already seeded ---");
            return;
        }

        User user1 = new User("Ivo", "ivo");
        User user2 = new User("Marie", "mar777");
        userService.createUser(user1);
        userService.createUser(user2);

        Account account1 = new Account(user1, "My account", 100.0);
        Account account2 = new Account(user2, "Savings for a car", 200.0);
        user1.attachAccount(account2);
        accountService.createAccount(account1);
        accountService.createAccount(account2);
//

        Loan loan1 = new Loan();
        loan1.setAmount(500.0);
        loan1.setInterestRate(5.0);
        loan1.setAttachedAccount(account1);
        loan1.setStartDate(LocalDate.now());
        loan1.setPeriod(12);

        Loan loan2 = new Loan();
        loan2.setAmount(1000.0);
        loan2.setInterestRate(4.5);
        loan2.setAttachedAccount(account2);
        loan2.setStartDate(LocalDate.now());
        loan2.setPeriod(24);

        Loan loan3 = new Loan();
        loan3.setAmount(1500.0);
        loan3.setInterestRate(4.0);
        loan3.setAttachedAccount(account2);
        loan3.setStartDate(LocalDate.now());
        loan3.setPeriod(36);


        loanService.createLoan(loan1);
        loanService.createLoan(loan2);
        loanService.createLoan(loan3);

        userService.updateUser(user1.getId(), user1);
        userService.updateUser(user2.getId(), user2);

        log.info("--- Default data seeded ---");
    }

}
