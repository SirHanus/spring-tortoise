package cz.mendelu.ea.domain.transaction;

import cz.mendelu.ea.domain.account.Account;
import cz.mendelu.ea.domain.account.AccountService;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("transactions")
@Validated
public class TransactionController {

    private final TransactionService transactionService;

    private final AccountService accountService;

    @Autowired
    public TransactionController(TransactionService transactionService, AccountService accountService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
    }

    @PostMapping(value = "", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @Valid
    public Transaction createTransaction(@RequestBody @Valid Transaction transaction) throws BadRequestException {
        Account source = accountService
                .getAccount(transaction.getSourceAccountId())
                .orElseThrow(() -> new BadRequestException("Source account not found"));
        Account target = accountService
                .getAccount(transaction.getTargetAccountId())
                .orElseThrow(() -> new BadRequestException("Target account not found"));

        return transactionService.processTransaction(source, target, transaction);
    }

}
