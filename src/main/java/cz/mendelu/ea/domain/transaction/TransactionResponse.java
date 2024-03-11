package cz.mendelu.ea.domain.transaction;

import cz.mendelu.ea.domain.account.Account;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionResponse {

    @NotNull
    Long id;

    @NotNull
    @Min(0)
    double amount;

    @NotNull
    Account source;

    @NotNull
    Account target;

    public TransactionResponse(Transaction transaction) {
        this.id = transaction.getId();
        this.amount = transaction.getAmount();
        this.source = transaction.getSource();
        this.target = transaction.getTarget();
    }
}
