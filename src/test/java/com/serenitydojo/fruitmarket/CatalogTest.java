package com.serenitydojo.fruitmarket;

import org.junit.Test;

import static org.junit.Assert.assertThrows;

import static com.serenitydojo.fruitmarket.Fruit.*;
import static org.assertj.core.api.Assertions.assertThat;

public class CatalogTest {

    @Test
    public void shouldBeAbleToUpdateTheCurrentPriceOfAFruit() {
        Catalog catalog = catalogWithSomeItemsAndPricesForEverything();
        assertThat(catalog.getPriceOf(Apple)).isEqualTo(8.00);
        catalog.setPriceOf(Apple, 4.00);
        assertThat(catalog.getPriceOf(Apple)).isEqualTo(4.00);
    }

    @Test
    public void shouldListAvailableFruitNamesAlphabetically() {
        Catalog catalog = catalogWithSomeItemsAndPricesForEverything();
        String availableFruits = catalog.getAvailableFruitNames();
        assertThat(availableFruits).isEqualTo("Apple, Banana, Pear");
    }

    @Test
    public void shoppingCartKeepsRunningTotal() {
        Catalog catalog = catalogWithSomeItemsAndPricesForEverything();
        ShoppingCart cart = new ShoppingCart(catalog);
        assertThat(cart.getTotal()).isEqualTo(0.0);
        cart.addItem(Pear, 1);
        assertThat(cart.getTotal()).isEqualTo(1.0);
        cart.addItem(Banana, 4);
        assertThat(cart.getTotal()).isEqualTo(9.0);
    }

    @Test
    public void throwsExceptionWhenNoSuchFruit() {
        Catalog catalog = catalogWithSomeItemsAndPricesForEverything();
        ShoppingCart cart = new ShoppingCart(catalog);

        FruitUnavailableException noSuchFruit = assertThrows(
                FruitUnavailableException.class,
                () -> cart.addItem(Orange, 99)
        );
        assertThat(noSuchFruit.getMessage()).isEqualTo("no such fruit");

        FruitUnavailableException noSuchAmount = assertThrows(
                FruitUnavailableException.class,
                () -> cart.addItem(Apple, 99)
        );
        assertThat(noSuchAmount.getMessage()).isEqualTo("no such amount");
    }


    @Test
    public void receive10percentDiscountWhenOver5kgForAnyFruit() {
        Catalog catalog = Catalog.withItems(
                new CatalogItem(Apple, 5),
                new CatalogItem(Apple, 1)
        );
        catalog.setPriceOf(Apple, 1.00);
        ShoppingCart cart = new ShoppingCart(catalog);
        cart.addItem(Apple, 5);
        assertThat(cart.getTotal()).isEqualTo(5.0);
        cart.addItem(Apple, 1);
        assertThat(cart.getTotal()).isEqualTo(5.4);
    }

    @Test
    public void noDiscountWhenDifferentFruits() {
        Catalog catalog = Catalog.withItems(
                new CatalogItem(Pear, 5),
                new CatalogItem(Apple, 1)
        );
        catalog.setPriceOf(Apple, 1.00);
        catalog.setPriceOf(Pear, 1.00);
        ShoppingCart cart = new ShoppingCart(catalog);
        cart.addItem(Pear, 5);
        assertThat(cart.getTotal()).isEqualTo(5.0);
        cart.addItem(Apple, 1);
        assertThat(cart.getTotal()).isEqualTo(6);
    }

    private static Catalog catalogWithSomeItemsAndPricesForEverything() {
        Catalog catalog = Catalog.withItems(
                new CatalogItem(Pear, 1),
                new CatalogItem(Apple, 2),
                new CatalogItem(Banana, 4)
        );
        catalog.setPriceOf(Pear, 1.0);
        catalog.setPriceOf(Apple, 8.00);
        catalog.setPriceOf(Banana, 2.0);
        return catalog;
    }
}
