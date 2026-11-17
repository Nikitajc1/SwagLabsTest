package configs;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ConfigReaderTest {
    private final ConfigReader config = new ConfigReader();

    @AfterEach
    void clearEnv() {
        System.clearProperty("env");
    }

    @Test
    void checkRealConfig() {
        String property = config.getProperty("db.local.url");

        assertNotNull(property);
    }

    @Test
    void checkFakeConfig() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> config.getProperty("fake")
        );

        assertEquals("Property not found: fake", exception.getMessage());
    }

    @Test
    void checkNullConfig() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> config.getProperty(null)
        );

        assertEquals("Property key cannot be null or blank"
                , exception.getMessage());
    }

    @Test
    void checkLocalConfig() {
        System.clearProperty("env");
        String env = config.getEnvironment();

        assertEquals("local", env);

    }

    @Test
    void checkDockerConfig() {
        System.clearProperty("env");
        String env = config.getEnvironment();

        assertEquals("docker", env);
    }

    @Test
    void checkStageConfig() {
        System.setProperty("env", "stage");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> config.getEnvironment()
        );

        assertEquals("Unknown environment: stage"
                , exception.getMessage());

    }
}
