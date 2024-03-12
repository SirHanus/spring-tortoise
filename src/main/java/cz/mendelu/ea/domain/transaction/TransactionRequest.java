package cz.mendelu.ea.domain.transaction;

import cz.mendelu.ea.domain.account.Account;
import cz.mendelu.ea.domain.account.AccountService;
import cz.mendelu.ea.utils.exceptions.NotFoundException;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionRequest {

    @NotNull
    @Min(0)
    double amount;

    @NotNull
    Long sourceAccountId;

    @NotNull
    Long targetAccountId;

    public void toTransaction(Transaction transaction, AccountService accountService) {
        transaction.setAmount(amount);

        Account sourceAccount = accountService
                .getAccount(sourceAccountId)
                .orElseThrow(NotFoundException::new);
        Account targetAccount = accountService
                .getAccount(targetAccountId)
                .orElseThrow(NotFoundException::new);
        transaction.setSourceAccount(sourceAccount);
        transaction.setTargetAccount(targetAccount);
    }
}
