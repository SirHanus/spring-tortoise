package cz.mendelu.ea.domain.account;

import cz.mendelu.ea.domain.user.User;
import cz.mendelu.ea.domain.user.UserService;
import cz.mendelu.ea.utils.exceptions.NotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequest {

    @NotNull
    private Long ownerId;

    @NotNull
    private String name;

    public void toAccount(Account account, UserService userService) {
        User user = userService
                .getUserById(ownerId)
                .orElseThrow(NotFoundException::new);
        account.setOwner(user);
        user.attachAccount(account);
    }

}
