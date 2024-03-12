package cz.mendelu.ea.domain.user;

import cz.mendelu.ea.domain.account.Account;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @NotNull
    Long id;

    @NotEmpty
    String name;

    @NotEmpty
    @Length(min = 3)
    String username;

    @NotNull
    List<Account> accounts;

    public void attachAccount(Account account) {
        this.accounts.add(account);
    }

}
