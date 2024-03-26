package cz.mendelu.ea.domain.transaction;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class TransactionResponse {

    @NotNull
    UUID id;

    @NotNull
    @Min(0)
    double amount;

    Long sourceAccountId;

    Long targetAccountId;

    public TransactionResponse(Transaction transaction) {
        this.id = transaction.getId();
        this.amount = transaction.getAmount();

        if (transaction.getSourceAccount() != null) {
            this.sourceAccountId = transaction.getSourceAccount().getId();
        }

        if (transaction.getTargetAccount() != null) {
            this.targetAccountId = transaction.getTargetAccount().getId();
        }
    }

}
