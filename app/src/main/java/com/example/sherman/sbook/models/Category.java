package com.example.sherman.sbook.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kenp on 29/10/2016.
 */

public class Category implements Serializable {
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    List<String> listIDBook;
    String backgound;

    public Category(String id, List<String> listIDBook, String backgound, String desr, String name) {
        this.id = id;
        this.listIDBook = listIDBook;
        this.backgound = backgound;
        this.desr = desr;
        this.name = name;
    }

    public String getDesr() {
        return desr;
    }

    public void setDesr(String desr) {
        this.desr = desr;
    }

    String desr;

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
