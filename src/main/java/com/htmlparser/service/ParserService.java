package com.htmlparser.service;

import com.htmlparser.ParseHtmlException;
import com.htmlparser.util.MyLogger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.*;
import java.util.Arrays;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;

public interface ParserService {

    // метод подсчета частоты слов в html документе
    void countWordsHtml(String url) throws ParseHtmlException;

    // создание таблицы в БД для сохранения статистики по частоте слов
    void createWordCountTable();

    //сохранение статистики в БД
    void saveWordCountToTable(String url);

    // Очистка таблицы в БД
    void dropWordCountTable();

    // получение статистики из БД по URL
    Map<String, Long> getStatsBySite(String url);

    //метод подсчета частоты слов
    default Map<String, Long> countWords(String inputText) {

        Map<String, Long> wordCount = Arrays.asList(inputText.split("\\s+|,|\\.|\\!|\\?|\"|;|:|\\[|\\]|\\(|\\)|\\n|\\r|\\t"))
                .stream().map(String::toUpperCase)
                .collect(Collectors.groupingBy(word -> word, Collectors.counting()))
                .entrySet().stream()
                .filter(x -> !x.getKey().isEmpty())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return wordCount;
    }

    //метод для вывода результатов вида key - value
    default String mapEntryToString(Map.Entry<String, Long> entry) {
        return entry.getKey() + " - " + entry.getValue();
    }

    //сохранение HTML страницы в файл
    default String htmlPageToFile(String url) throws ParseHtmlException {

        Document doc = null;
        try {
            doc = Jsoup.connect(url)
                    .userAgent("Chrome/88.0.4324.192 Safari/537.36")
                    .maxBodySize(0)
                    .get();
        } catch (IOException | IllegalArgumentException e) {
            throw new ParseHtmlException("Не удалось получить страницу по заданному URL");
        }
        String[] urlAddress = url.split("//");
        String fileName = urlAddress[1];
        try (BufferedWriter out = new BufferedWriter(
                new FileWriter(fileName + ".html"))) {
            out.write(doc.outerHtml());
        } catch (IOException e) {
            MyLogger.log(Level.SEVERE, "Не удалось записать Html файл" + e.getMessage());
        }
        return fileName + ".html";
    }

    // загрузка HTML страницы
    default String fileToHtmlString(String path) throws IOException {

        StringBuilder html = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String inputString;
            while ((inputString = reader.readLine()) != null) {
                html.append(inputString);
            }
        } catch (IOException e) {
            MyLogger.log(Level.SEVERE, "Не удалось прочитать Html файл" + e);
        }
        return html.toString();
    }

}
