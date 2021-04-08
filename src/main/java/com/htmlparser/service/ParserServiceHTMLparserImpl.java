package com.htmlparser.service;

import com.htmlparser.ParseHtmlException;
import com.htmlparser.dao.WordCountDao;
import com.htmlparser.dao.WordCountJDBCImpl;
import com.htmlparser.util.MyLogger;
import org.htmlparser.Parser;
import org.htmlparser.beans.StringBean;
import org.htmlparser.util.ParserException;

import java.util.Map;
import java.util.logging.Level;

public class ParserServiceHTMLparserImpl implements ParserService {

    String docText;
    WordCountDao wordCount = new WordCountJDBCImpl();

    @Override
    public void createWordCountTable() {
        wordCount.createWordCountTable();
    }

    @Override
    public void saveWordCountToTable(String url) {

        countWords(docText).entrySet().stream()
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

    @Override
    public void countWordsHtml(String html) throws ParseHtmlException {

        StringBean sb = new StringBean();
        sb.setLinks(false);
        sb.setReplaceNonBreakingSpaces(true);
        sb.setCollapse(true);
        Parser parser = new Parser();
        try {
            MyLogger.log(Level.INFO, "Парсим HTML документ...");
            parser.setInputHTML(html);
            parser.visitAllNodesWith(sb);
        } catch (ParserException e) {
            MyLogger.log(Level.WARNING, "Не удалось распарсить html страницу (HTMLparser)");
            throw new ParseHtmlException("Не удалось распарсить html страницу");
        }
        docText = sb.getStrings();
        if (docText == null) docText = "";
        MyLogger.log(Level.INFO, "Считаем частоту слов на странице...");
        countWords(docText).entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed().thenComparing(Map.Entry.comparingByKey()))
                .forEach(x -> System.out.println(mapEntryToString(x)));
    }
}
