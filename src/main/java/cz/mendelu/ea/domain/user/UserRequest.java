package cz.mendelu.ea.domain.user;

import cz.mendelu.ea.domain.account.Account;
import cz.mendelu.ea.domain.account.AccountService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
public class UserRequest {

    @NotEmpty
    String name;

    @NotEmpty
    @Length(min = 3)
    String username;

    @NotNull
    List<Long> accountIds;

    public void toUser(User user, AccountService accountService) {
        user.setName(name);
        user.setUsername(username);

        // bidirectional relationship between User and Account
        List<Account> accounts = accountIds.stream()
                .map(accountService::getAccount)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
        accounts.forEach(user::attachAccount);
    }

}
