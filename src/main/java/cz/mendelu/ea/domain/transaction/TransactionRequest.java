package cz.mendelu.ea.domain.transaction;

import cz.mendelu.ea.domain.account.Account;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionRequest {
    @NotNull
    Long id;

    @NotNull
    @Min(0)
    double amount;

    @NotNull
    Long source;

    @NotNull
    Long target;
}
