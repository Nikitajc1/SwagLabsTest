package dbTests;

import java.sql.*;

public class UserCreator {
    public static void main(String[] args) {

        String url = "jdbc:postgresql://localhost:5432/qa_shop";
        String username = "nikita";
        String password = "";
        String sql = "INSERT INTO users (name, email, age) VALUES (?, ?, ?)";

        try (Connection connection =
                     DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection
                     .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, "TestUser");
            statement.setString(2, "testuser@mail.com");
            statement.setInt(3, 25);

            int rowsInserted = statement.executeUpdate();
            System.out.println("Добавлено строк: " + rowsInserted);

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    int userId = keys.getInt(1);
                    System.out.println("Создан пользователь с ID: " + userId);
                }
            }

        } catch (SQLException e) {
            System.out.println("Ошибка " + e.getMessage());

        }
    }
}
