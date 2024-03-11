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
    private Long ownerId;

    public AccountRequest(Long ownerId) {
        this.ownerId = ownerId;
    }

    //wtf kde je zbytek dat?
    public void toAccount(Account account, User user, AccountService accountService){
        account.setId((long) (accountService.getAllAccounts().size()-1));
        account.setOwner(user);
    }
}
