package com.example.sherman.sbook.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Book {
    private String title;
    private String coverUrl;
    private String author;
    private String owner;
    private String tags;
    private String publisher;
    private String rating;

    public Book() {

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
}
