package lk.ijse.dep13.springbackend.api;


import lk.ijse.dep13.springbackend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@RestController
@RequestMapping("/users")
public class UserHttpController {

//    public UserHttpController(Connection connection) {
//        this.connection = connection;
//    }

    @Autowired  // if a connection comes, and set for this
    private Connection connection;



    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json")
    public User signUp(@RequestBody User user) throws SQLException {
        try(PreparedStatement stm = connection.prepareStatement("INSERT INTO \"user\" (email, password, profile_picture) VALUES (?, ?, ?)")) {
            stm.setString(1, user.email());
            stm.setString(2, user.password());
            stm.setString(3, user.profilePicture());
            stm.executeUpdate();
            return user;
        }

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
