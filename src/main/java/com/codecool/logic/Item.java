package com.codecool.logic;

import java.util.Random;

public class Item {

    private int id;
    private String productName;
    private float price;
    private final int MAX_LIMIT = 100000;


    public Item(String productName, float price) {
        Random generateId = new Random();
        this.id = generateId.nextInt(MAX_LIMIT);
        this.productName = productName;
        this.price = price;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }


    public String getProductName() {
        return this.productName;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", MAX_LIMIT=" + MAX_LIMIT +
                '}';
    }


}
