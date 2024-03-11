package cz.mendelu.ea.domain.user;

import cz.mendelu.ea.domain.account.Account;
import cz.mendelu.ea.domain.account.AccountService;
import cz.mendelu.ea.utils.exceptions.NotFoundException;
import cz.mendelu.ea.utils.reponse.ArrayResponse;
import cz.mendelu.ea.utils.reponse.ObjectResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
@Validated
public class UserController {



    private final AccountService accountService;

    private final UserService userService;

    @Autowired
    public UserController(AccountService accountService, UserService userService) {
        this.accountService = accountService;
        this.userService = userService;
    }
    @GetMapping(value = "/size", produces = "application/json")
    @Valid
    public ObjectResponse<Integer> userCount() {
        return new ObjectResponse<>(userService.getAllUsers().size());
    }

    @GetMapping(value = "", produces = "application/json")
    @Valid
    public ArrayResponse<UserResponse> getUsers() {
        var userResponses = userService.getAllUsers().stream().map(UserResponse::new).toList();

        return new ArrayResponse<>(userResponses, userResponses.size());
    }


    @GetMapping(value = "/{id}", produces = "application/json")
    @Valid
    public ObjectResponse<UserResponse> getUserById(@PathVariable Long id) {
        User user = userService.getUser(id).orElseThrow(NotFoundException::new);
        return new ObjectResponse<>(new UserResponse(user));
    }

    @PostMapping(value = "", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @Valid
    public ObjectResponse<UserResponse> createAndAddUser(@RequestBody @Valid UserRequest userRequest) {
        User user = new User();
        userRequest.toUser(user, accountService);
        userService.addUser(user);
        return new ObjectResponse<>(new UserResponse(user));
    }

}
