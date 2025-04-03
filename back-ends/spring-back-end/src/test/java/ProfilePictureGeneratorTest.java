import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;


class ProfilePictureGeneratorTest {

    @Test
    void generateBase64DataUrl() throws IOException {
        byte[] bytes = getClass().getResourceAsStream("/profile-pic.png").readAllBytes();
        String base64 = Base64.getEncoder().encodeToString(bytes);
        System.out.println("data:image/png;base64," + base64);
    }
}
