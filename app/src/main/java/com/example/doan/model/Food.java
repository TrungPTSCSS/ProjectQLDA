package com.example.doan.model;

public class Food {
    private String Name,Image, NguyenLieu,CachLam,MenuId ;
    public Food(){
    }

    public Food(String name, String image, String nguyenLieu, String cachLam, String menuId) {
        Name = name;
        Image = image;
        NguyenLieu = nguyenLieu;
        CachLam = cachLam;
        MenuId = menuId;
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

    public String getNguyenLieu() {
        return NguyenLieu;
    }

    public void setNguyenLieu(String nguyenLieu) {
        NguyenLieu = nguyenLieu;
    }

    public String getCachLam() {
        return CachLam;
    }

    public void setCachLam(String cachLam) {
        CachLam = cachLam;
    }

    public String getMenuId() {
        return MenuId;
    }

    public void setMenuId(String menuId) {
        MenuId = menuId;
    }
}
