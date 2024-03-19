package cz.mendelu.ea.domain.user;

import cz.mendelu.ea.domain.account.AccountService;
import cz.mendelu.ea.utils.exceptions.NotFoundException;
import cz.mendelu.ea.utils.response.ArrayResponse;
import cz.mendelu.ea.utils.response.ObjectResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.List;

@RestController
@RequestMapping("users")
@Validated
public class UserController {

    private final UserService userService;

    private AccountService accountService;

    @Autowired
    public UserController(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @GetMapping(value = "", produces = "application/json")
    @Valid
    public ArrayResponse<UserResponse> getUsers() {
//        List<UserResponse> userResponses = userService
//                .getAllUsers()
//                .stream()
//                .map(UserResponse::new)
//                .toList();
//        return new ArrayResponse<>(userResponses);

        return ArrayResponse.of(
                userService.getAllUsers(),
                UserResponse::new
        );
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @Valid
    public ObjectResponse<UserResponse> getUserById(@PathVariable Long id) {
        User user = userService
                .getUserById(id)
                .orElseThrow(NotFoundException::new);
        return ObjectResponse.of(user, UserResponse::new);
    }

    @PostMapping(value = "", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @Valid
    public ObjectResponse<UserResponse> createUser(@RequestBody @Valid UserRequest userRequest) {
        User user = new User();
        userRequest.toUser(user, accountService);

        userService.createUser(user);

        return ObjectResponse.of(user, UserResponse::new);
    }


    @DeleteMapping(value = "/{id}", produces = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

}
