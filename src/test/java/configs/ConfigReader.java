package configs;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class ConfigReader {
    private Properties properties;

    public ConfigReader() {
        properties = new Properties();

        try (FileInputStream file =
                     new FileInputStream("src/test/resources/config.properties")) {

            properties.load(file);

        } catch (IOException e) {
            throw new RuntimeException("Не удалось загрузить config.properties", e);
        }
    }

    public String getProperty(String key) {
        if (key == null || key.isBlank()) {
            throw new IllegalArgumentException("Property key cannot be null or blank");
        }

        String value = properties.getProperty(key);

        if (value == null) {
            throw new IllegalArgumentException("Property not found: " + key);
        }
        return value;
    }

    public String getEnvironment() {
        String environment = System.getProperty("env");

        environment = Objects.requireNonNullElse(environment, "local");

        if (!environment.equals("local") && !environment.equals("docker")) {
            throw new IllegalArgumentException("Unknown environment: " + environment);
        }

        return environment;
    }
}
