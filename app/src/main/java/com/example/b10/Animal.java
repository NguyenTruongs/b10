package com.example.b10;

public class Animal {
    private int id;
    private String creature_name;
    private String image;
    private String price;
    private String content;

    public Animal() {
    }

    public Animal(int id, String creature_name, String image, String price, String content) {
        this.id = id;
        this.creature_name = creature_name;
        this.image = image;
        this.price = price;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreature_name() {
        return creature_name;
    }

    public void setCreature_name(String creature_name) {
        this.creature_name = creature_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
