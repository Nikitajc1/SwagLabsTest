package dbTests;

import dbHelpers.InfoDB;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DockerDBTests {
    InfoDB info = new InfoDB();
    private final String url = info.getDockerUrl();
    private final String username = info.getDockerUsername();
    private final String password = info.getPassword2();
    private final String sqlFindUserById = info.getSqlSelectUserById();
    private final String sqlCreateUser = info.getSqlCreateUser();
    private final String sqlDeleteUser = info.getSqlDeleteUser();
    private final String sqlUpdateAgeOfUser = info.getSqlUpdateAgeOfUser();
    private final String query = "SELECT current_database(), current_user;";

    @Test
    void testDataBase() throws SQLException {

        try (Connection connection =
                     DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(query)) {


            try (ResultSet result = statement.executeQuery()) {
                assertTrue(result.next(), "DB not found");

                String actualDB = result.getString("current_database");
                String actualUser = result.getString("current_user");

                assertAll(
                        () -> assertEquals("qa_shop_compose", actualDB),
                        () -> assertEquals("qa_user", actualUser)
                );

            }
        }
    }
}
