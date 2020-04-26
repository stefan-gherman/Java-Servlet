package com.codecool.logic;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private List<Item> itemsList;

    public Cart() {
        this.itemsList = new ArrayList<Item>();
    }

    public void addItemToCart(Item item) {
        this.itemsList.add(item);
    }

    public void removeItemFromCart(Item item) {
        if(itemsList.contains(item)) {
            itemsList.remove(item);
        }
    }

    public List<Item> getItemsList() {
        return itemsList;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "itemsList=" + itemsList +
                '}';
    }

    public float calculateCartPrice() {
        float totalSum = 0;
        for (Item item: itemsList
             ) {
            totalSum += item.getPrice();
        }
        return totalSum;
    }
}
