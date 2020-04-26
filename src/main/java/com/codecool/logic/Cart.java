package com.codecool.logic;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {

    private List<Item> itemsList;

    public Cart() {
        this.itemsList = new ArrayList<Item>();
    }

    public void addItemToCart(Item item) {
        this.itemsList.add(item);
    }

    public void removeItemFromCart(Item item) {
        for (Item currentItem : itemsList ){
            if(item.getProductName().equals(currentItem.getProductName())){
                itemsList.remove(currentItem);
                return;
            }
        }
    }

    public List<Item> getItemsList() {
        return itemsList;
    }

    public List<String> getItemsListAsArray() {
        List<String> cartAsArray= new ArrayList<String>();

        for (Item currentItem : itemsList ){
            cartAsArray.add(String.format("productName:%s? price:%s?*", currentItem.getProductName(), currentItem.getPrice()));
        }
        return cartAsArray;
    }

    public String getCartContentJSON() {
        return new Gson().toJson(itemsList).toString();
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
