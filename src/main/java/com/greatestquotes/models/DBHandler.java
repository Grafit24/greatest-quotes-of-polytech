package com.greatestquotes.models;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBHandler {
    private static Connection connection = null;

    public static Connection restartConnection() throws SQLException, IOException {
        String configFilePath = "config.properties";
        FileInputStream propsInput = new FileInputStream(configFilePath);

        Properties prop = new Properties();
        prop.load(propsInput);

        String connectionString = String.format("jdbc:mysql://%s:%s/%s",
                prop.getProperty("DB_HOST"), prop.getProperty("DB_PORT"), prop.getProperty("DB_NAME"));
       return DriverManager.getConnection(
               connectionString, prop.getProperty("DB_USER"), prop.getProperty("DB_PASSWORD"));
    }

    public static Connection getConnection() {
        if (connection == null)
            try {
                connection = restartConnection();
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }

        return connection;
    }
}
