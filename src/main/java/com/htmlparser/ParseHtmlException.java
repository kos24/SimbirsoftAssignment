package com.htmlparser;

import java.io.IOException;

//исключение для отлавливания ошибок на уровне ParserService
public class ParseHtmlException extends IOException {

    public ParseHtmlException(String message) {
        super(message);
    }

    public ParseHtmlException(String message, Throwable cause) {
        super(message, cause);
    }
}
