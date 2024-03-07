package cz.mendelu.ea.domain.transaction;

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
    Long sourceAccountId;

    @NotNull
    Long targetAccountId;

}
