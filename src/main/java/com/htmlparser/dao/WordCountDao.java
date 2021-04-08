package com.htmlparser.dao;

import java.util.Map;

public interface WordCountDao {

    // создание таблицы в БД для сохранения статистики по частоте слов
    void createWordCountTable();

    // удаление таблицы в БД для сохранения статистики по частоте слов
    void dropWordCountTable();

    //сохранение статистики в БД
    void saveWordCount(String url, String word, Long count);

    // Очистка таблицы в БД
    void cleanWordCountTable();

    // получение статистики из БД по URL
    Map<String, Long> getStatsBySite(String url);


}
