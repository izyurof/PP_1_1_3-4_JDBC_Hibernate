package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
@UtilityClass
public class Util {
    private static final Properties PROPERTIES = new Properties();
    private static final String URL_KEY = "url";
    private static final String USERNAME_KEY = "username";
    private static final String PASSWORD_KEY = "username";

    static {
        loadProperties();
    }

    private static void loadProperties() {
        try (InputStream inputStream = Util.class.getClassLoader().getResourceAsStream("hibernate.properties")) {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection openConnection() {
        try {
            return DriverManager.getConnection(PROPERTIES.getProperty(URL_KEY),
                    PROPERTIES.getProperty(USERNAME_KEY),
                    PROPERTIES.getProperty(PASSWORD_KEY));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration().addAnnotatedClass(User.class);
        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
        return configuration.buildSessionFactory();
    }
}
