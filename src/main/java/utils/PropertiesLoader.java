package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {
    private static final String PROPERTIES_FILE_NAME = "application.properties";
    private static final Properties PROPERTIES;

    static {
        PROPERTIES = new Properties();

        try (InputStream inputStream = getInputStream(PROPERTIES_FILE_NAME)) {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static InputStream getInputStream(String fileName) {
        return PropertiesLoader.class.getClassLoader().getResourceAsStream(fileName);
    }

    public static String getProperty(String name) {
        return PROPERTIES.getProperty(name);
    }
}
