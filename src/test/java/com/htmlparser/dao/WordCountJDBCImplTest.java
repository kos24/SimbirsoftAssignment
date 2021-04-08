package com.htmlparser.dao;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class WordCountJDBCImplTest {

    static WordCountDao wordCount;

    private final String url = "www.test.com";
    private final String word = "ТЕСТ";
    private final Long count = 5L;

    @BeforeAll
    static void init() {
        wordCount = new WordCountJDBCImpl();
    }

    @Test
    void dropWordCountTable() {
        try {
            wordCount.dropWordCountTable();
            wordCount.dropWordCountTable();
        } catch (Exception e) {
            fail("При тестировании удаления таблицы WORD_COUNT произошло исключение\n" + e.getMessage());
        }
    }

    @Test
    void createWordCountTable1() {
        try {
            wordCount.dropWordCountTable();
            wordCount.createWordCountTable();
        } catch (Exception e) {
            fail("При тестировании создания таблицы WORD_COUNT произошло исключение\n" + e.getMessage());
        }
    }

    @Test
    void saveWordCount() {
        try {
            wordCount.dropWordCountTable();
            wordCount.createWordCountTable();
            wordCount.saveWordCount(url,word,count);

            Map<String, Long> stats = wordCount.getStatsBySite(url);
            Map.Entry<String,Long> entry = stats.entrySet().iterator().next();
            String wordTest = entry.getKey();
            Long countTest = entry.getValue();

            if (!word.equals(wordTest) || !count.equals(countTest)) {
                fail("Статистика была некорректно сохранена в базу данных");
            }
        } catch (Exception e) {
            fail("Во время тестирования сохранения статистики произошло исключение\n" + e.getMessage());
        }
    }

    @Test
    void getStatsBySite() {
        try {
            wordCount.dropWordCountTable();
            wordCount.createWordCountTable();
            wordCount.saveWordCount(url,word,count);
            Map<String, Long> stats = wordCount.getStatsBySite(url);

            if (stats.size() != 1) {
                fail("Проверьте корректность работы метода сохранения статистики, удаления или создания таблицы");
            }
        } catch (Exception e) {
            fail("При попытке достать статистику из базы данных произошло исключение\n" + e.getMessage());
        }
    }

    @Test
    void cleanWordCountTable() {
        try {
            wordCount.dropWordCountTable();
            wordCount.createWordCountTable();
            wordCount.saveWordCount(url,word,count);
            Map<String, Long> stats = wordCount.getStatsBySite(url);
            wordCount.cleanWordCountTable();

            if (wordCount.getStatsBySite(url).size() != 0) {
                fail("Метод очистки таблицы работает не корректно");
            }
        } catch (Exception e) {
            fail("При тестировании очистки таблицы WORD_COUNT произошло исключение\n" + e.getMessage());
        }

    }
}