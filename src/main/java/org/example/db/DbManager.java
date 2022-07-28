package org.example.db;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Slf4j
public class DbManager {
    private static DbManager instance = null;

//    private static final String DB_URL = loadURL();

    private static final String DB_URL = "jdbc:postgresql://localhost:5433/postgres?user=postgres&password=admin";
    private DbManager() {
    }

    private static String loadURL() {
        try (InputStream input = new FileInputStream("application.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            return prop.getProperty("db.url");
        } catch (IOException ex) {
            log.info(String.format("IO Exception while loading properties: %s", ex.getMessage()));
        }
        return null;
    }

    public static synchronized DbManager getInstance() {
        if (instance == null) {
            instance = new DbManager();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
}