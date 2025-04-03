package lk.ijse.dep13.springbackend.api;


import lk.ijse.dep13.springbackend.entity.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.*;
import java.util.Objects;

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
        try(PreparedStatement stm1 = connection.prepareStatement ("SELECT * FROM \"user\" WHERE email = ?");
            PreparedStatement stm2 = connection.prepareStatement("INSERT INTO \"user\" (email, password, profile_picture, full_name) VALUES (?, ?, DEFAULT, ?)")) {

            stm1.setString(1, user.getEmail());
            if (stm1.executeQuery().next())
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");


            stm2.setString(1, user.getEmail());
            String encryptedPassword = DigestUtils.sha256Hex(user.getPassword());
            stm2.setString(2, encryptedPassword);
            stm2.setString(3, user.getFullName());
            stm2.executeUpdate();
            user.setPassword(encryptedPassword);
            user.setProfilePicture(User.DEFAULT_PROFILE_PICTURE);
            return user;
        }

    }

    @GetMapping("/me")
    public User getUserInfo(@SessionAttribute(value = "user",required = false) String email ) throws SQLException {
        if (email == null)  throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email");
        try(var stm = connection.prepareStatement("SELECT * FROM \"user\" WHERE email=?")){
            stm.setString(1, email);
            ResultSet rst = stm.executeQuery();
            rst.next();
            return new User(rst.getString("full_name"),email,rst.getString("password"), Objects.requireNonNullElse(rst.getString("profile_picture"),
                    User.DEFAULT_PROFILE_PICTURE));

        }
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
