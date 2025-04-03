package lk.ijse.dep13.springbackend.api;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthHttpController {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/login")
    public String logIn() {
        return "Log in";
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/logout")
    public String deleteUser() {
        return "Log out";
    }
}
