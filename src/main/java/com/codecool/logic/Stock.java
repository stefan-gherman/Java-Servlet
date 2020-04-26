package com.codecool.logic;

import com.sun.tools.javac.jvm.Items;

import java.util.Set;
import java.util.TreeSet;

public class Stock {
    private Set<Item> itemsInStock;


    public Stock(Set<Item> initialStock) {
        this.itemsInStock = new TreeSet<>();
        this.itemsInStock.addAll(initialStock);
    }

    public void addToStock(Item item) {
        this.itemsInStock.add(item);
    }

    public void removeFromStock(Item item) {
        if(itemsInStock.contains(item)) {
            itemsInStock.remove(item);
        }
    }

    public Set<Item> getItemsInStock() {
        return itemsInStock;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "itemsInStock=" + itemsInStock +
                '}';
    }
}
