package lk.ijse.dep13.springbackend.api;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserHttpController {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public String signUp() {
        return "Create new user";
    }

    @GetMapping("/me")
    public String getUserInfo() {
        return "get authenticated user info";
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/me")
    public String updateUser(){
        return "Update authenticated user information";
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/me")
    public String deleteUser() {
        return "delete authenticated user account";
    }
}
