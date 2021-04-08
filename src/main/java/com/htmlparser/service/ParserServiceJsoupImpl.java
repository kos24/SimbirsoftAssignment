package com.htmlparser.service;

import com.htmlparser.ParseHtmlException;
import com.htmlparser.dao.WordCountDao;
import com.htmlparser.dao.WordCountJDBCImpl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Map;

public class ParserServiceJsoupImpl implements ParserService {

    Document doc = null;
    WordCountDao wordCount = new WordCountJDBCImpl();

    @Override
    public void countWordsHtml(String html) throws ParseHtmlException {

        try {
            doc = Jsoup.parse(html);
        } catch (Exception e) {
            throw new ParseHtmlException("Не удалось распарсить html страницу");
        }
        // сортировка слов по убыванию (если одинаковая частота, то сортировка по алфавиту)
        countWords(doc.text()).entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed().thenComparing(Map.Entry.comparingByKey()))
                .forEach(x -> System.out.println(mapEntryToString(x)));
    }

    @Override
    public void createWordCountTable() {
        wordCount.createWordCountTable();
    }

    @Override
    public void saveWordCountToTable(String url) {

        countWords(doc.text()).entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed().thenComparing(Map.Entry.comparingByKey()))
                .forEach(x -> wordCount.saveWordCount(url, x.getKey(), x.getValue()));
    }

    @Override
    public Map<String, Long> getStatsBySite(String url) {
        return wordCount.getStatsBySite(url);
    }

    @Override
    public void dropWordCountTable() {
        wordCount.dropWordCountTable();
    }
}
