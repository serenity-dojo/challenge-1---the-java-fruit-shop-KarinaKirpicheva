package com.serenitydojo.fruitmarket;

import java.util.*;
import java.util.stream.Collectors;

public class Catalog {
    private final List<CatalogItem> availableFruits = new ArrayList<>();
    private final Map<Fruit, Double> fruitToPricePerKg = new HashMap<>();

    public void setPriceOf(Fruit fruit, double pricePerKg) {
        fruitToPricePerKg.put(fruit, pricePerKg);
    }

    public static Catalog withItems(CatalogItem... catalogItems) {
        Catalog catalog = new Catalog();
        catalog.availableFruits.addAll(Arrays.asList(catalogItems));
        return catalog;
    }

    public List<CatalogItem> getAvailableFruits() {
        return availableFruits;
    }

    public Double getPriceOf(Fruit fruit) {
        return fruitToPricePerKg.get(fruit);
    }

    public CatalogItem getFruit(Fruit fruit, int amountInKg) {
        List<CatalogItem> itemsForFruit = getAvailableFruits().stream()
                .filter(it -> it.getFruit().equals(fruit)).collect(Collectors.toList());
        if (itemsForFruit.isEmpty()) {
            throw new FruitUnavailableException("no such fruit");
        }
        Optional<CatalogItem> optionalCatalogItem = itemsForFruit.stream()
                .filter(it -> it.getAmountInKg() == amountInKg)
                .findFirst();
        if (optionalCatalogItem.isPresent()) {
            return optionalCatalogItem.get();
        }
        throw new FruitUnavailableException("no such amount");
    }

    public String getAvailableFruitNames() {
        return getAvailableFruits().stream().map(it -> it.getFruit().name()).sorted().collect(Collectors.joining(", "));
    }
}
