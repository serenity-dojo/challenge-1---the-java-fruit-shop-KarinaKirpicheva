package com.serenitydojo;

public class CatalogItem {

    private Fruit fruit;
    private double pricePerKg;
    private int quantity;

    public CatalogItem(Fruit fruit, int quantity, double pricePerKg) {
        this.fruit = fruit;
        this.pricePerKg = pricePerKg;
        this.quantity = quantity;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPricePerKg(double pricePerKg) {
        this.pricePerKg = pricePerKg;
    }

    public double getPricePerKg() {
        return pricePerKg;
    }

}