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
    private String rating;
    private String interesting;

    public Book(String title, String coverUrl, String author, String tags, String publisher, String inter, String owner) {
        this.title = title;
        this.coverUrl = coverUrl;
        this.author = author;
        this.interesting = inter;
        this.tags = tags;
        this.publisher = publisher;
        this.owner = owner;
    }

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

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

    public String getRating() {
        return rating;
    }

    public String getInteresting() {
        return interesting;
    }
}
