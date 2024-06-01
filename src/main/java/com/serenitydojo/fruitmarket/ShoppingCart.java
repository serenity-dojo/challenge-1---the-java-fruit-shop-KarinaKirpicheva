package com.serenitydojo.fruitmarket;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ShoppingCart {

    private final Catalog catalog;
    private final List<CatalogItem> items = new ArrayList<>();

    public ShoppingCart(Catalog catalog) {
        this.catalog = catalog;
    }

    public double getTotal() {
        double sum = items.stream().mapToDouble(it -> catalog.getPriceOf(it.getFruit()) * it.getAmountInKg()).sum();
        Map<Fruit, List<CatalogItem>> fruitToItems = items.stream().collect(Collectors.groupingBy(CatalogItem::getFruit));
        boolean anyFruitAbove5kg = fruitToItems.values().stream().anyMatch(items -> items.stream().mapToInt(CatalogItem::getAmountInKg).sum() > 5);
        if (anyFruitAbove5kg) {
            int discountInPercent = 10;
            return sum * (1 - (discountInPercent/ 100.));
        }
        return sum;
    }

    public void addItem(Fruit fruit, int amountInKg) {
        items.add(catalog.getFruit(fruit, amountInKg));
    }
}
