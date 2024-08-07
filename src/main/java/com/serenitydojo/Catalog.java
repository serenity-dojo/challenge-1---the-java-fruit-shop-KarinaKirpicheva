package com.serenitydojo;

import java.util.*;
import java.util.stream.Collectors;

public class Catalog {

    private static Map<Fruit, CatalogItem> currentStock = new HashMap<>();

    public static Catalog withItems(CatalogItem... catalogItems) {

        Catalog catalog = new Catalog();
        for(CatalogItem item: catalogItems){
            currentStock.put(item.getFruit(), item);
        }

        return catalog;
    }

    public void setPriceOf(Fruit fruit, double price) {

        currentStock.get(fruit).setPricePerKg(price);
    }

    public Double getPriceOf(Fruit fruit) throws FruitUnavailableException {

        if(!currentStock.containsKey(fruit))
            throw new FruitUnavailableException("Fruit is not available");

        return currentStock.get(fruit).getPricePerKg();
    }

    public void setQuantityOf(Fruit fruit, int quantity) {

        currentStock.get(fruit).setQuantity(quantity);
    }

    public int getQuantityOf(Fruit fruit) throws FruitUnavailableException {

        if(!currentStock.containsKey(fruit) || currentStock.get(fruit).getQuantity() == 0)
            throw new FruitUnavailableException("Fruit is not available");
        else
            return currentStock.get(fruit).getQuantity();
    }

    public List<String> getAvailableFruits() {

        return currentStock.values().stream()
                .map(value -> value.getFruit().name())
                .sorted()
                .collect(Collectors.toList());
    }

    public Map<Fruit,Double> getPriceList() {

        Map<Fruit,Double> result = new HashMap<>();

        for(Fruit fruit: currentStock.keySet()){
            result.put(fruit, currentStock.get(fruit).getPricePerKg());
        }

        return result;
    }
}