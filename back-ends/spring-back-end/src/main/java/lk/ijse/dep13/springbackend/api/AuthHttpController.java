package lk.ijse.dep13.springbackend.api;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthHttpController {

    @PostMapping("/login")
    public String logIn() {
        return "Log in";
    }

    @DeleteMapping("/logout")
    public String deleteUser() {
        return "Log out";
    }
}
