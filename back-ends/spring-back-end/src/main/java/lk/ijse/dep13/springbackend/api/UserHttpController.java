package lk.ijse.dep13.springbackend.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;

@RestController
@RequestMapping("/users")
public class UserHttpController {

//    public UserHttpController(Connection connection) {
//        this.connection = connection;
//    }

    @Autowired  // if a connection comes, and set for this
    private Connection connection;



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
