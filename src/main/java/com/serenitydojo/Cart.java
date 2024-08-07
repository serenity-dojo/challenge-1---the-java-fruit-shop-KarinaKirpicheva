package com.serenitydojo;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private static final double appliedDiscount = 0.9;
    private static Map<Fruit, Integer> shopping = new HashMap<>();

    public static void add(Fruit fruit, int quantity) {

        if (shopping.containsKey(fruit))
            shopping.put(fruit, shopping.get(fruit) + quantity);
        else
            shopping.put(fruit, quantity);
    }

    public static void remove(Fruit fruit, int quantity) {

        if (shopping.get(fruit) < quantity)
            quantity = shopping.get(fruit);

        shopping.put(fruit, shopping.get(fruit) - quantity);
    }

    public int getQuantity(Fruit fruit) {

        return shopping.get(fruit);
    }

    public static double getTotalCost(Catalog catalog) {

        Map<Fruit,Double> priceList = catalog.getPriceList();
        double totalCost = 0.0;
        for(Fruit fruit: shopping.keySet()) {

            Double price = priceList.get(fruit);
            int quantity = shopping.get(fruit);

            if(quantity >=5)
                totalCost = totalCost + ((quantity * price) * appliedDiscount);
            else
                totalCost = totalCost + (quantity * price);
        }
        return totalCost;
    }
}
