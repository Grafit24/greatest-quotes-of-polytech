package com.greatestquotes.models;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBHandler {
    public static Connection connection;

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

    public static Connection getConnection() throws SQLException {
        try {
            connection = restartConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
