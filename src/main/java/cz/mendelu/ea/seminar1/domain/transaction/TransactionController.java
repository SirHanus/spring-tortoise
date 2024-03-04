package cz.mendelu.ea.seminar1.domain.transaction;

import cz.mendelu.ea.seminar1.domain.account.Account;
import cz.mendelu.ea.seminar1.domain.account.AccountService;
import cz.mendelu.ea.seminar1.utils.exceptions.InsufficientFunds;
import cz.mendelu.ea.seminar1.utils.exceptions.NotFound;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("transactions")
@Validated
@Slf4j
public class TransactionController {

    private final AccountService accountService;

    private final TransactionService transactionService;
    @Autowired
    public TransactionController(TransactionService transactionService, AccountService accountService){
        this.transactionService = transactionService;
        this.accountService = accountService;

    }

    @GetMapping(value="", produces = "application/json")
    @Valid
    public List<Transaction> getTransactions(){
        return this.transactionService.getAllTransactions();
    }
    @PostMapping(value="", produces="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @Valid
    public Transaction createTransaction(@RequestBody @Valid Transaction transaction) {

        Optional<Account> sourceAccount = accountService.getAccountById(transaction.getSourceAccountId());
        Optional<Account> targetAccount = accountService.getAccountById(transaction.getTargetAccountId());
        log.info(transaction.toString());
        if (sourceAccount.isEmpty() || targetAccount.isEmpty()) {
            throw new NotFound();
        }
        if (sourceAccount.get().getBalance() < transaction.getAmount()) {
            throw new InsufficientFunds();
        }
        return this.transactionService.processTransaction(transaction, sourceAccount.get(), targetAccount.get());

    }

}
