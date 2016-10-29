package com.example.sherman.sbook.models;

/**
 * Created by kenp on 29/10/2016.
 */

public class Notification {
    private String avatar;
    private String content;

    public Notification(String avatar, String content) {
        this.avatar = avatar;
        this.content = content;
    }

    public String getAvatar() {

        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
