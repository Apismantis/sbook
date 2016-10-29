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

    public Book() {

    private String id;

    public Book() {

    }

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getId() {
        return id;
    }
}
