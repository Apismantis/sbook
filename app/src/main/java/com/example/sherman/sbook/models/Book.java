package com.example.sherman.sbook.models;

/**
 * Created by kenp on 29/10/2016.
 */

public class Book {
    private String title;
    private String coverUrl;
    private String author;
    private String owner;
    private String tags;
    private String publisher;

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getOwner() {
        return owner;
    }

    public String getTags() {
        return tags;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getCoverUrl() {
        return coverUrl;
    }
}
