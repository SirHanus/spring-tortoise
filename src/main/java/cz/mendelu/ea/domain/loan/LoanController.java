package cz.mendelu.ea.domain.loan;

import cz.mendelu.ea.domain.account.Account;
import cz.mendelu.ea.domain.account.AccountRequest;
import cz.mendelu.ea.domain.account.AccountResponse;
import cz.mendelu.ea.domain.account.AccountService;
import cz.mendelu.ea.utils.exceptions.NotFoundException;
import cz.mendelu.ea.utils.response.ArrayResponse;
import cz.mendelu.ea.utils.response.ObjectResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("loans")
@Validated
public class LoanController {
    private final LoanService loanService;

    private final AccountService accountService;

    @Autowired
    public LoanController(LoanService loanService, AccountService accountService) {
        this.loanService = loanService;
        this.accountService = accountService;
    }

    @GetMapping(value = "", produces = "application/json")
    @Valid
    public ArrayResponse<LoanResponse> getLoans() {
        return ArrayResponse.of(loanService.getLoans(),LoanResponse::new);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @Valid
    public ObjectResponse<LoanResponse> getLoan(@PathVariable UUID id) {
        Loan loan = loanService
                .getLoan(id)
                .orElseThrow(NotFoundException::new);
        return ObjectResponse.of(loan, LoanResponse::new);
    }

    @PostMapping(value = "", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @Valid
    public ObjectResponse<LoanResponse> createAccount(@RequestBody @Valid LoanRequest loanRequest) {
        Loan loan = new Loan();
        loanRequest.toLoan(loan, accountService);

        loanService.createLoan(loan);

        return ObjectResponse.of(loan, LoanResponse::new);
    }

    @PutMapping(value = "/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Valid
    @Transactional
    public ObjectResponse<LoanResponse> updateAccount(@PathVariable UUID id, @RequestBody @Valid LoanRequest loanRequest) {
        Loan loan = loanService.getLoan(id)
                .orElseThrow(NotFoundException::new);
        loanRequest.toLoan(loan, accountService);

        loanService.updateLoan(id, loan);

        return ObjectResponse.of(loan, LoanResponse::new);
    }
}



