package com.htmlparser.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

// POJO class
@Table(name = "WORD_COUNT")
public class WordCount {

    @Id
    private Long id;

    @Column
    private String url;

    @Column
    private String word;

    @Column
    private int count;

    public WordCount() {
    }

    public WordCount(String url, String word, int count) {
        this.url = url;
        this.word = word;
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
