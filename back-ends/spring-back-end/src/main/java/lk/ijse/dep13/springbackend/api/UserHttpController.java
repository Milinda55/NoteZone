package lk.ijse.dep13.springbackend.api;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserHttpController {

    @PostMapping
    public String signUp() {
        return "Create new user";
    }

    @GetMapping("/me")
    public String getUserInfo() {
        return "get authenticated user info";
    }

    @PatchMapping("/me")
    public String updateUser(){
        return "Update authenticated user information";
    }

    @DeleteMapping("/me")
    public String deleteUser() {
        return "delete authenticated user account";
    }
}
