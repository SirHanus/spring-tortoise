package cz.mendelu.ea.domain.account;

import cz.mendelu.ea.domain.user.User;
import cz.mendelu.ea.domain.user.UserService;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Data
public class AccountRequest {

    @NotEmpty
    private Integer ownerId;

    public AccountRequest(Integer id) {
        this.ownerId = id;
    }

    //wtf kde je zbytek dat?
    public void toAccount(Account account, UserService userService, AccountService accountService){
        Optional<User> user = userService.getUser(Long.valueOf(this.ownerId));
        account.setId((long) (accountService.getAllAccounts().size()-1));
        user.ifPresent(account::setOwner);
    }
}
