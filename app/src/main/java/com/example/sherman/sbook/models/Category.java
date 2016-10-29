package com.example.sherman.sbook.models;

import java.util.List;

/**
 * Created by kenp on 29/10/2016.
 */

public class Category {
    List<String> listIDBook;
    String backgound;

    public Category(String name, List<String> list) {
        this.name = name;
        listIDBook = null;
    }

    public String getBackgound() {
        return backgound;
    }

    public void setBackgound(String backgound) {
        this.backgound = backgound;
    }

    String name;

    public List<String> getListIDBook() {
        return listIDBook;
    }

    public void setListIDBook(List<String> listIDBook) {
        this.listIDBook = listIDBook;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
