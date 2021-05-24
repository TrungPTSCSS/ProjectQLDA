package com.example.appnote;

public class FoodView {
    public String name;
    public int id;
    public String nguyenlieu;
    public String congthuc;
    public String Createdday;
    public byte[] Image;
    //    public int idUser;
    public FoodView(int id, String name,String nguyenlieu,String congthuc, String created,byte[] img ){
        this.id=id;
        this.name=name;
        this.nguyenlieu=nguyenlieu;
        this.congthuc=congthuc;
        this.Createdday=created;
        this.Image=img;
    }
}
