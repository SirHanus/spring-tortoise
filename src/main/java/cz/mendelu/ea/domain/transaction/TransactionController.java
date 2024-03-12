package cz.mendelu.ea.domain.transaction;

import cz.mendelu.ea.domain.account.AccountService;
import cz.mendelu.ea.utils.response.ObjectResponse;
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
        Transaction transaction = new Transaction();
        transactionRequest.toTransaction(transaction, accountService);

        transactionService.processTransaction(transaction);

        return ObjectResponse.of(transaction, TransactionResponse::new);
    }

}
