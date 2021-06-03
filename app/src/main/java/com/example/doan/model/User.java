package com.example.doan.model;

public class User {
    private String Name;
    private  String Password;
    private String id;
    public User(){

    }

    public User(String name, String password) {
        Name = name;
        Password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}