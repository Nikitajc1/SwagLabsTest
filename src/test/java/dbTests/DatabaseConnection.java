package dbTests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/qa_shop";
        String username = "nikita";
        String password = "";

        try
                (Connection connection = DriverManager.getConnection(url, username, password)) {

            System.out.println("Подключение успешно к " + connection.getCatalog());

        } catch (SQLException e) {
            System.out.println("Ошибка " + e.getMessage());

        }
    }
}
