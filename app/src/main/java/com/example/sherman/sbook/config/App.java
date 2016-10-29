package com.example.sherman.sbook.config;

/**
 * Created by VõVân on 10/29/2016.
 */
public class App {
    private static App ourInstance = new App();

    public static App getInstance() {
        return ourInstance;
    }

    private App() {
    }
}
