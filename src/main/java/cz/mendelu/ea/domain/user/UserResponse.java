package cz.mendelu.ea.domain.user;

import cz.mendelu.ea.domain.account.Account;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
@AllArgsConstructor
public class UserResponse {

    public UserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.username = user.getUsername();
        this.accountIds = user.getAccounts().stream().map(Account::getId).toList();

        this.avgBalance = user.getAccounts().stream()
                .mapToDouble(Account::getBalance)
                .average()
                .orElse(0);
    }

    @NotNull
    Long id;

    @NotEmpty
    String name;

    @NotEmpty
    @Length(min = 3)
    String username;

    @NotNull
    List<Long> accountIds;

    double avgBalance;

}
