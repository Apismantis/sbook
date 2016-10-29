package com.example.sherman.sbook.models;

/**
 * Created by kenp on 30/10/2016.
 */

public class Notification {
    public String bookId;
    public boolean seen;

    public Notification() {

    }

    public String getBookId() {
        return bookId;
    }

    public boolean isSeen() {
        return seen;
    }
}
