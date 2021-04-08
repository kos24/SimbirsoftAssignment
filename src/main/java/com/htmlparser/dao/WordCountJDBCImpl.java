package com.htmlparser.dao;

import com.htmlparser.util.MyLogger;
import com.htmlparser.util.Util;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class WordCountJDBCImpl implements WordCountDao {

    private Connection connection = Util.getDBConnection();

    public WordCountJDBCImpl() {
    }

    @Override
    public void createWordCountTable() {

        String sql = "CREATE TABLE WORD_COUNT (id INT NOT NULL AUTO_INCREMENT," +
                "url VARCHAR(100) NULL," +
                "word VARCHAR(200) NULL, " +
                "count INT NULL," +
                "PRIMARY KEY (id));";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.execute();
            MyLogger.log(Level.INFO, "Таблица WORD_COUNT создана");
        } catch (SQLException e) {
            MyLogger.log(Level.WARNING, "Ошибка при создании WORD_COUNT таблицы " + e.getMessage());
        }
    }

    @Override
    public void dropWordCountTable() {

        String sql = "DROP TABLE WORD_COUNT;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.execute();
            MyLogger.log(Level.INFO, "Таблица WORD_COUNT удалена");
        } catch (SQLException e) {
            MyLogger.log(Level.WARNING, "Таблица WORD_COUNT не существует " + e.getMessage());
        }
    }

    @Override
    public void saveWordCount(String url, String word, Long count) {
        String sql = "INSERT INTO WORD_COUNT(url, word, count) VALUES (?,?,?);";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, url);
            preparedStatement.setString(2, word);
            preparedStatement.setLong(3, count);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            MyLogger.log(Level.WARNING, "Ошибка записи в таблицу WORD_COUNT " + e.getMessage());
        }
    }


    @Override
    public void cleanWordCountTable() {

        String cleanUsers = "DELETE FROM WORD_COUNT;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(cleanUsers)) {
            preparedStatement.execute();
            MyLogger.log(Level.INFO, "Таблица WORD_COUNT очищена");
        } catch (SQLException e) {
            MyLogger.log(Level.WARNING, "Ошибка при очистке таблицы WORD_COUNT" + e.getMessage());
        }
    }

    @Override
    public Map<String, Long> getStatsBySite(String url) {

        Map<String, Long> stats = new HashMap<>();
        String getStat = "SELECT * FROM WORD_COUNT WHERE URL = ?;";

        try (PreparedStatement statement = connection.prepareStatement(getStat)) {
            statement.setString(1, url);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                stats.put(resultSet.getString(3), resultSet.getLong(4));
            }
        } catch (SQLException e) {
            MyLogger.log(Level.WARNING, "Ошибка при получении данных из базы" + e.getMessage());
        }
        return stats;
    }
}
