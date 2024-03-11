package cz.mendelu.ea.domain.transaction;

import cz.mendelu.ea.domain.account.Account;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Transaction {

    @NotNull
    Long id;

    @NotNull
    @Min(0)
    double amount;

    @NotNull
    Account source;

    @NotNull
    Account target;

    public Transaction(TransactionRequest transactionRequest, Account source, Account target) {
        this.id = transactionRequest.getId();
        this.amount = transactionRequest.getAmount();
        this.source = source;
        this.target = target;
    }
}
