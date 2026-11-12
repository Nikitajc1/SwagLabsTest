package dbHelpers;

public class InfoDB {
    private final String url = "jdbc:postgresql://localhost:5432/qa_shop";
    private final String username = "nikita";
    private final String password = "";
    private final String sqlSelectUserById = "SELECT * FROM users WHERE id = ?";
    private final String sqlCreateUser = "INSERT INTO users (name, email, age) VALUES (?, ?, ?)";
    private final String sqlDeleteUser = "DELETE FROM users WHERE id = ?";
    private final String sqlUpdateAgeOfUser = "UPDATE users SET age = ? WHERE id = ?";

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getSqlSelectUserById() {
        return sqlSelectUserById;
    }

    public String getSqlCreateUser() {
        return sqlCreateUser;
    }

    public String getSqlDeleteUser() {
        return sqlDeleteUser;
    }

    public String getSqlUpdateAgeOfUser() {
        return sqlUpdateAgeOfUser;
    }
}
