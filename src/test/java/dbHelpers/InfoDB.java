package dbHelpers;

import configs.ConfigReader;

public class InfoDB {
    private final String password2 = "qa_pass";
    private final String password = "";
    private final String sqlSelectUserById = "SELECT * FROM users WHERE id = ?";
    private final String sqlCreateUser = "INSERT INTO users (name, email, age) VALUES (?, ?, ?)";
    private final String sqlDeleteUser = "DELETE FROM users WHERE id = ?";
    private final String sqlUpdateAgeOfUser = "UPDATE users SET age = ? WHERE id = ?";
    private final ConfigReader config;

    public InfoDB() {
        config = new ConfigReader();
    }

    public String getLocalUrl() {
        return config.getProperty("db.local.url");
    }

    public String getLocalUsername() {
        return config.getProperty("db.local.username");
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

    public String getDockerUrl() {
        return config.getProperty("db.docker.url");
    }

    public String getDockerUsername() {
        return config.getProperty("db.docker.username");
    }

    public String getPassword2() {
        return password2;
    }
}
