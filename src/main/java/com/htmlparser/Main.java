package com.htmlparser;

import com.htmlparser.service.ParserService;
import com.htmlparser.service.ParserServiceHTMLparserImpl;
import com.htmlparser.service.ParserServiceJsoupImpl;
import com.htmlparser.util.MyLogger;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;

public class Main {

    public static void main(String[] args) {

        ParserService parser;

        if (args.length != 0 && "SAX".equalsIgnoreCase(args[0])) {
            System.out.println("HTMLparser");
            MyLogger.log(Level.INFO, "используется HTMLparser");
            parser = new ParserServiceHTMLparserImpl();
        } else {
            parser = new ParserServiceJsoupImpl();
            System.out.println("JSoup");
            MyLogger.log(Level.INFO, "используется JSoup");
        }
//        parser.dropWordCountTable();
        parser.createWordCountTable();

        try (Scanner scanner = new Scanner(System.in)) {
            String url = "";
            boolean correctUrl = false;
            do {
                try {
                    System.out.println("Пожалуйста введите URL:");
                    url = scanner.nextLine();
                    if ("exit".equalsIgnoreCase(url)) {
                        break;
                    }
                    String pathToFile = parser.htmlPageToFile(url);
                    String html = parser.fileToHtmlString(pathToFile);
                    parser.countWordsHtml(html);
                    parser.saveWordCountToTable(pathToFile);
                    correctUrl = true;
                } catch (ParseHtmlException e) {
                    System.out.println("Не удалось получить страницу по заданному URL");
                    System.out.println("- для выхода из программы введите exit");
                    MyLogger.log(Level.WARNING, e.getMessage());
                } catch (IOException e) {
                    MyLogger.log(Level.SEVERE, e.getMessage());
                }
            } while (!correctUrl);
        }
    }
}
