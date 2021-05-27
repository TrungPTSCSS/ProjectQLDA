package com.example.doan.model;

public class Categoryfood {
    private String Name;
    private String Image;
    public Categoryfood()
    {

    }

    public Categoryfood(String name, String image) {
        Name = name;
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
