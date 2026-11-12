package dbTests;
import java.sql.*;

public class UserRepository {
    public static void main(String[] args) {

        String url = "jdbc:postgresql://localhost:5432/qa_shop";
        String username = "nikita";
        String password = "";
        String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection connection =
                     DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, 1);

            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    System.out.println(
                            result.getInt("id") + " " +
                                    result.getString("name") + " " +
                                    result.getString("email") + " " +
                                    result.getInt("age")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Ошибка " + e.getMessage());

        }
    }
}
