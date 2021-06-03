package com.example.doan.model;

public class Food {
    private String Name,Image, NguyenLieu,CachLam,MenuId,Id ;
    public Food(){

    }

    public Food(String name, String image, String nguyenLieu, String cachLam, String menuId) {
        Name = name;
        Image = image;
        NguyenLieu = nguyenLieu;
        CachLam = cachLam;
        MenuId = menuId;
    }
    public Food(String id, String name, String image, String nguyenLieu, String cachLam, String menuId) {
        Id=id;
        Name = name;
        Image = image;
        NguyenLieu = nguyenLieu;
        CachLam = cachLam;
        MenuId = menuId;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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
