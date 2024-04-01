package cz.mendelu.ea.domain.loan;

import cz.mendelu.ea.domain.account.Account;
import cz.mendelu.ea.domain.account.AccountResponse;
import cz.mendelu.ea.domain.account.AccountService;
import cz.mendelu.ea.utils.exceptions.NotFoundException;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class LoanRequest {

    @NotNull
    @Min(0)
    private Double amount = 0.0;

    @NotNull
    @Min(0)
    private double interestRate = 0.0;

    @NotNull
    @Min(1)
    private int period;

    @NotNull
    private Long accountId;
    public void toLoan(Loan loan, AccountService accountService) {
        loan.setAmount(amount);
        loan.setInterestRate(interestRate);
        loan.setPeriod(period);
        Account attachedAccount = accountService.getAccount(accountId).orElseThrow(NotFoundException::new);
        loan.setAttachedAccount(attachedAccount);
        loan.setStartDate(LocalDate.now());
    }

}
