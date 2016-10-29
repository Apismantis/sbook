package com.example.sherman.sbook;

/**
 * Created by Sherman on 10/29/2016.
 */
public class Book {
    private String name;

    public Book(String name, int photo) {
        this.name = name;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    private int photo;

    public int getPhoto() {
        return photo;
    }

}
