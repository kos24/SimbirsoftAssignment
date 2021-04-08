package com.htmlparser.service;

import com.htmlparser.util.MyLogger;
import org.htmlparser.Parser;
import org.htmlparser.beans.StringBean;
import org.htmlparser.util.ParserException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.*;

class ParserServiceTest {

    static ParserService parserServiceJsoup;
    static ParserService parserServiceHTMLparser;

    Map<String, Long> validationSet  = new HashMap<String, Long>() {{
        put("ТЕСТ", 2L);
        put("ПАРСИМ", 1L);
        put("ДОКУМЕНТ", 2L);
        put("HTML", 1L);

    }};

    @BeforeAll
    static void init() {
        parserServiceJsoup = new ParserServiceJsoupImpl();
        parserServiceHTMLparser = new ParserServiceHTMLparserImpl();
    }

    @Test
    void countWordsHtmlJsoup() {

        String html = "<html><head><title>Тест тест документ</title></head>" +
                "<body><p>Парсим HTML документ</p></body></html>";
            Document doc = Jsoup.parse(html);
            Map<String, Long> testSet = parserServiceJsoup.countWords(doc.text());
            assertEquals(validationSet,testSet);
    }

    @Test
    void countWordsHtmlparser() {

        String html = "<html><head><title>Тест тест документ</title></head>" +
                "<body><p>Парсим HTML документ</p></body></html>";
        StringBean sb = new StringBean();
        sb.setLinks(false);
        sb.setReplaceNonBreakingSpaces(true);
        sb.setCollapse(true);
        Parser parser = new Parser();
        try {
            parser.setInputHTML(html);
            parser.visitAllNodesWith(sb);
        } catch (ParserException e) {
            MyLogger.log(Level.WARNING, "Ошибка при тестировании countWordsHtmlparser" + e.getMessage());
        }
        Map<String, Long> testSet = parserServiceHTMLparser.countWords(sb.getStrings());
        assertEquals(validationSet,testSet);
    }
}