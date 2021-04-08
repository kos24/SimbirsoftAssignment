package com.htmlparser.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

public class Util {

    public static final String URL = "jdbc:mysql://localhost:3306/word_count";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "rootUSER";

    // метод создания соединения с БД
    public static Connection getDBConnection()  {

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            MyLogger.log(Level.INFO,"Соединение установлено");
        } catch (SQLException e) {
            e.printStackTrace();
            MyLogger.log(Level.SEVERE,"Ошибка соединения");
        }
        return connection;
    }
}
