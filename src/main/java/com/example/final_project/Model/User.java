package com.example.final_project.Model;

import org.jetbrains.annotations.NotNull;

import java.util.Enumeration;
import java.util.ResourceBundle;

public class User extends ResourceBundle {
    private String id;
    private String password;
    private String Role;
    public User(String id){
        this.id = id;
    }
    @Override
    protected Object handleGetObject(@NotNull String key) {
        return this.id;
    }

    @NotNull
    @Override
    public Enumeration<String> getKeys() {
        return null;
    }
}
