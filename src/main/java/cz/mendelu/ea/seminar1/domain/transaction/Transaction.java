package cz.mendelu.ea.seminar1.domain.transaction;


import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Data;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class Transaction {

    @NotNull
    private Long id;

    @NotNull
    @Min((0))
    private double amount;


    @JoinColumn(name = "source_account_id", nullable = false)
    private Long sourceAccountId;


    @JoinColumn(name = "target_account_id", nullable = false)
    private Long targetAccountId;

}