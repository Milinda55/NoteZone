package lk.ijse.dep13.springbackend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class WebRootConfig {

    public WebRootConfig() throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
    }

    @Scope("prototype")  // create this again and again
    @Bean
    public Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/dep13_note_app", "postgres", "psql");
    }
}
