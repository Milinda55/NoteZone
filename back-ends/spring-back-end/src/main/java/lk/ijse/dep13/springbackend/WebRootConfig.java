package lk.ijse.dep13.springbackend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.annotation.RequestScope;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
@PropertySource("classpath:application.properties")  // access both System.getEnv and System.getProperties environments
public class WebRootConfig {

    public WebRootConfig() throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
    }

    @RequestScope
    @Bean
    public Connection connection(@Value("${app.datasource.url}") String url,
                                 @Value("${app.datasource.username}") String username,
                                 @Value("${app.datasource.password}") String password) throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
