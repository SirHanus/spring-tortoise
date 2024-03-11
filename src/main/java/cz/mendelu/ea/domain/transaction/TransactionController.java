package cz.mendelu.ea.domain.transaction;

import cz.mendelu.ea.domain.account.Account;
import cz.mendelu.ea.domain.account.AccountService;
import cz.mendelu.ea.utils.reponse.ObjectResponse;
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
    public ObjectResponse<TransactionResponse> createTransaction(@RequestBody @Valid TransactionRequest transactionRequest) throws BadRequestException {
        Account source = accountService
                .getAccount(transactionRequest.getSource())
                .orElseThrow(() -> new BadRequestException("Source account not found"));
        Account target = accountService
                .getAccount(transactionRequest.getTarget())
                .orElseThrow(() -> new BadRequestException("Target account not found"));

        return new ObjectResponse<>(new TransactionResponse(transactionService.processTransaction(source, target, new Transaction(transactionRequest, source, target))));
    }

}
