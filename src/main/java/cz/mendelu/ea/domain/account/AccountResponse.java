package cz.mendelu.ea.domain.account;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AccountResponse {

    @NotNull
    private Long id;

    @NotNull
    private Long ownerId;

    @NotNull
    @Min(0)
    private double balance;

    @NotNull
    private int transactionCount;

    public AccountResponse(Account account) {
        this.id = account.getId();
        this.ownerId = account.getOwner().getId();
        this.balance = account.getBalance();
        this.transactionCount = account.getTransactionCount();
    }

}
