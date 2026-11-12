package dbTests;
import java.sql.*;

public class UserUpdater {
    public static void main(String[] args) {

        String url = "jdbc:postgresql://localhost:5432/qa_shop";
        String username = "nikita";
        String password = "";
        String sql = "UPDATE users SET age = ? WHERE id = ?";

        try (Connection connection =
                     DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, 28);
            statement.setInt(2, 1);

            int rowsUpdated = statement.executeUpdate();

            System.out.println("Изменено строк: " + rowsUpdated);

        } catch (SQLException e) {
            System.out.println("Ошибка " + e.getMessage());

        }
    }

}
