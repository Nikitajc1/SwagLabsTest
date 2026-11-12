package dbTests;
import apiHelpers.InfoApi;
import dbHelpers.InfoDB;
import models.Booking;
import models.BookingDates;
import models.BookingResponse;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.devtools.latest.network.model.Response;

import java.sql.*;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class UserDBTests {
    InfoDB info = new InfoDB();
    InfoApi infoApi = new InfoApi();
    private final String url = info.getUrl();
    private final String username = info.getUsername();
    private final String password = info.getPassword();
    private final String sqlFindUserById = info.getSqlSelectUserById();
    private final String sqlCreateUser = info.getSqlCreateUser();
    private final String sqlDeleteUser = info.getSqlDeleteUser();
    private final String sqlUpdateAgeOfUser = info.getSqlUpdateAgeOfUser();


    @Test
    void testUserData() throws SQLException {

        try (Connection connection =
                     DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sqlFindUserById)) {

            statement.setInt(1, 1);


            try (ResultSet result = statement.executeQuery()) {
                assertTrue(result.next(), "Пользователь с ID = 1 не найден");

                int actualId = result.getInt("id");
                String actualName = result.getString("name");
                String actualEmail = result.getString("email");
                int actualAge = result.getInt("age");

                assertAll(
                        () -> assertEquals(1, actualId),
                        () -> assertEquals("Ivan", actualName),
                        () -> assertEquals("ivan@mail.com", actualEmail),
                        () -> assertEquals(28, actualAge)
                );

            }
        }
    }

    @Test
    void testUserNotFound() throws SQLException {
        try (Connection connection =
                     DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sqlFindUserById)) {

            statement.setInt(1, 999);


            try (ResultSet result = statement.executeQuery()) {
                assertFalse(result.next(), "Пользователь неожиданно найден");
            }
        }
    }

    @Test
    void testCreateAndDeleteUser() throws SQLException {
        try (Connection connection =
                     DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection
                     .prepareStatement(sqlCreateUser, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, "JDBCTest");
            statement.setString(2, "jdbc_test_2026@mail.com");
            statement.setInt(3, 25);

            int rowsInserted = statement.executeUpdate();
            assertEquals(1, rowsInserted, "Пользователь не был создан");

            try (ResultSet key = statement.getGeneratedKeys()) {
                assertTrue(key.next());
                int userId = key.getInt(1);
                System.out.println("Создан пользователь с ID: " + userId);

                try (PreparedStatement statement2 = connection
                        .prepareStatement(sqlDeleteUser)) {
                    statement2.setInt(1, userId);
                    int rowsDeleted = statement2.executeUpdate();
                    assertEquals(1, rowsDeleted, "Пользователь не был удалён");
                }

                try (PreparedStatement statement3 = connection
                        .prepareStatement(sqlFindUserById)) {
                    statement3.setInt(1, userId);
                    try (ResultSet result = statement3.executeQuery()) {
                        assertFalse(result.next(), "Пользователь неожиданно найден");
                    }
                }
            }
        }
    }

    @Test
    void testTransactionCommit() throws SQLException {
        try (Connection connection =
                     DriverManager.getConnection(url, username, password)) {

            connection.setAutoCommit(false);

            try (PreparedStatement statement = connection.prepareStatement(sqlUpdateAgeOfUser)) {

                statement.setInt(1, 35);
                statement.setInt(2, 1);

                int rowsUpdated = statement.executeUpdate();
                assertEquals(1, rowsUpdated, "Пользователь не был обновлён");

                connection.commit();

            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }

            try (PreparedStatement statement2 = connection.prepareStatement(sqlFindUserById)) {

                statement2.setInt(1, 1);

                try (ResultSet result = statement2.executeQuery()) {
                    assertTrue(result.next(), "Пользователь с ID = 1 не найден");

                    int actualId = result.getInt("id");
                    String actualName = result.getString("name");
                    String actualEmail = result.getString("email");
                    int actualAge = result.getInt("age");

                    assertAll(
                            () -> assertEquals(1, actualId),
                            () -> assertEquals("Ivan", actualName),
                            () -> assertEquals("ivan@mail.com", actualEmail),
                            () -> assertEquals(35, actualAge)
                    );

                }

            }
        }
    }

    @Test
    void createBookingWithPojo() {
        BookingDates testDates = new BookingDates("2026-11-15", "2026-11-20");
        Booking testObject = new Booking(
                "Automation",
                "Tester",
                500,
                true,
                testDates,
                "Breakfast"
        );

        BookingResponse response = given()
                .spec(infoApi.getBookingSpec())
                .body(testObject)
                .when().post("/booking")
                .then()
                .statusCode(200)
                .extract().as(BookingResponse.class);

        assertNotNull(response);

        int id = response.getBookingid();
        assertTrue(id > 0);

        Booking response2 = given()
                .spec(infoApi.getBookingSpec())
                .when().get("/booking/" + id)
                .then()
                .statusCode(200)
                .extract().as(Booking.class);

        assertAll(
                () -> assertEquals("Automation", response2.getFirstname()),
                () -> assertEquals("Tester", response2.getLastname()),
                () -> assertEquals(500, response2.getTotalprice()),
                () -> assertTrue(response2.isDepositpaid()),
                () -> assertEquals("Breakfast", response2.getAdditionalneeds()),
                () -> assertEquals("2026-11-15", response2.getBookingdates().getCheckin()),
                () -> assertEquals("2026-11-20", response2.getBookingdates().getCheckout())
        );
    }

}
