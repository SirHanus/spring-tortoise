package cz.mendelu.ea.domain.transaction;

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
    Long sourceAccountId;

    @NotNull
    Long targetAccountId;

    public TransactionResponse(Transaction transaction) {
        this.id = transaction.getId();
        this.amount = transaction.getAmount();
        this.sourceAccountId = transaction.getSourceAccount().getId();
        this.targetAccountId = transaction.getTargetAccount().getId();
    }

}
