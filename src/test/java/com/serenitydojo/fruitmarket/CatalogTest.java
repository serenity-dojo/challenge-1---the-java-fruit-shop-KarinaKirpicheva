package com.serenitydojo.fruitmarket;

import com.serenitydojo.Cart;
import com.serenitydojo.FruitUnavailableException;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import com.serenitydojo.Catalog;
import com.serenitydojo.CatalogItem;

import java.util.List;

import static com.serenitydojo.Cart.*;
import static org.assertj.core.api.Assertions.assertThat;
import static com.serenitydojo.Fruit.*;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class CatalogTest {

    @Test
    public void get_price_per_kilo() throws FruitUnavailableException {

        Catalog catalog = Catalog.withItems(
                new CatalogItem(Apple,1, 4.00),
                new CatalogItem(Orange, 1, 5.50),
                new CatalogItem(Banana, 1,6.00),
                new CatalogItem(Pear, 1,4.50)
        );

        assertThat(catalog.getPriceOf(Apple)).isEqualTo(4.00);
        assertThat(catalog.getPriceOf(Orange)).isEqualTo(5.50);
        assertThat(catalog.getPriceOf(Banana)).isEqualTo(6.00);
        assertThat(catalog.getPriceOf(Pear)).isEqualTo(4.50);
    }

    @Test
    public void update_price_per_kilo() throws FruitUnavailableException {

        Catalog catalog = Catalog.withItems(
                new CatalogItem(Apple,1, 4.00)
        );

        assertThat(catalog.getPriceOf(Apple)).isEqualTo(4.00);
        catalog.setPriceOf(Apple, 3.75);
        assertThat(catalog.getPriceOf(Apple)).isEqualTo(3.75);
    }

    @Test
    public void get_quantity_by_kilo() throws FruitUnavailableException {

        Catalog catalog = Catalog.withItems(
                new CatalogItem(Apple,1000, 4.00),
                new CatalogItem(Orange, 1234, 5.50),
                new CatalogItem(Banana, 9999,6.00),
                new CatalogItem(Pear, 100,4.50)
        );

        assertThat(catalog.getQuantityOf(Apple)).isEqualTo(1000);
        assertThat(catalog.getQuantityOf(Orange)).isEqualTo(1234);
        assertThat(catalog.getQuantityOf(Banana)).isEqualTo(9999);
        assertThat(catalog.getQuantityOf(Pear)).isEqualTo(100);
    }

    @Test
    public void update_quantity_by_kilo() throws FruitUnavailableException {

        Catalog catalog = Catalog.withItems(
                new CatalogItem(Apple,1, 4.00)
        );

        assertThat(catalog.getQuantityOf(Apple)).isEqualTo(1);
        catalog.setQuantityOf(Apple, 23);
        assertThat(catalog.getQuantityOf(Apple)).isEqualTo(23);
    }

    @Test
    public void list_fruit_alphabetically() {

        Catalog catalog = Catalog.withItems(
                new CatalogItem(Apple,1, 4.00),
                new CatalogItem(Orange, 1, 5.50),
                new CatalogItem(Banana, 1,6.00),
                new CatalogItem(Pear, 1,4.50)
        );

        List<String> availableFruits = catalog.getAvailableFruits();
        assertThat(availableFruits.get(0)).isEqualTo(Apple.name());
        assertThat(availableFruits.get(1)).isEqualTo(Banana.name());
        assertThat(availableFruits.get(2)).isEqualTo(Orange.name());
        assertThat(availableFruits.get(3)).isEqualTo(Pear.name());
    }

    @Test
    public void get_quantity_when_fruit_not_in_stock() {

        Catalog catalog = Catalog.withItems(
                new CatalogItem(Pear, 0, 1.00)
        );

        assertThatExceptionOfType(FruitUnavailableException.class).isThrownBy(
                () -> { catalog.getQuantityOf(Pear); }
        );
    }

    @Test
    public void get_quantity_when_fruit_not_in_catalog() {

        Catalog catalog = Catalog.withItems(
                new CatalogItem(Apple, 0, 1.00)
        );

        assertThatExceptionOfType(FruitUnavailableException.class).isThrownBy(
                () -> { catalog.getQuantityOf(Lemon); }
        );
    }

    @Test
    public void add_regular_items_to_cart() throws FruitUnavailableException {

        Catalog catalog = Catalog.withItems(
                new CatalogItem(Apple,15, 4.00),
                new CatalogItem(Orange, 15, 5.50),
                new CatalogItem(Banana, 15,6.00),
                new CatalogItem(Pear, 15,4.00)
        );

        Cart cart = new Cart();

        cart.add(Pear, 3);
        Assertions.assertThat(cart.getTotalCost(catalog)).isEqualTo(12.00);

        cart.add(Apple, 3);
        assertThat(cart.getTotalCost(catalog)).isEqualTo(24.00);

        cart.add(Banana, 2);
        assertThat(cart.getTotalCost(catalog)).isEqualTo(36.00);
    }

    @Test
    public void add_more_of_item_to_cart() throws FruitUnavailableException {

        Catalog catalog = Catalog.withItems(
                new CatalogItem(Apple,15, 4.00),
                new CatalogItem(Orange, 15, 5.50),
                new CatalogItem(Banana, 15,6.00),
                new CatalogItem(Pear, 15,4.00)
        );

        Cart cart = new Cart();

        cart.add(Apple, 1);
        assertThat(cart.getTotalCost(catalog)).isEqualTo(4.00);

        cart.add(Apple, 1);
        assertThat(cart.getTotalCost(catalog)).isEqualTo(8.00);

        cart.add(Apple, 2);
        assertThat(cart.getTotalCost(catalog)).isEqualTo(16.00);
    }

    @Test
    public void remove_items_from_cart() throws FruitUnavailableException {

        Catalog catalog = Catalog.withItems(
                new CatalogItem(Apple,15, 4.00),
                new CatalogItem(Orange, 15, 5.50),
                new CatalogItem(Banana, 15,6.00),
                new CatalogItem(Pear, 15,4.00)
        );

        Cart cart = new Cart();
        Cart.add(Pear, 3);
        Cart.add(Apple, 3);
        Cart.add(Banana, 3);

        assertThat(getTotalCost(catalog)).isEqualTo(42.00);

        remove(Banana, 1);
        assertThat(cart.getTotalCost(catalog)).isEqualTo(36.00);

        remove(Pear, 1);
        assertThat(cart.getTotalCost(catalog)).isEqualTo(32.00);
    }

    @Test
    public void cannot_remove_more_items_than_in_cart() throws FruitUnavailableException {

        Catalog catalog = Catalog.withItems(
                new CatalogItem(Apple,15, 4.00),
                new CatalogItem(Orange, 15, 5.50),
                new CatalogItem(Banana, 15,6.00),
                new CatalogItem(Pear, 15,4.00)
        );

        Cart cart = new Cart();
        cart.add(Pear, 3);

        assertThat(cart.getQuantity(Pear)).isEqualTo(3);
        assertThat(cart.getTotalCost(catalog)).isEqualTo(12.00);

        cart.remove(Pear, 5);

        assertThat(cart.getQuantity(Pear)).isEqualTo(0);
        assertThat(cart.getTotalCost(catalog)).isEqualTo(0.00);
    }

    @Test
    public void include_discount_when_over_item_quantity() throws FruitUnavailableException {

        Catalog catalog = Catalog.withItems(
                new CatalogItem(Apple,15, 4.00),
                new CatalogItem(Orange, 15, 5.50),
                new CatalogItem(Banana, 15,6.00),
                new CatalogItem(Pear, 15,4.00)
        );

        Cart cart = new Cart();
        cart.add(Pear, 4);

        assertThat(cart.getTotalCost(catalog)).isEqualTo(16.00);
        cart.add(Pear, 4);
        assertThat(cart.getTotalCost(catalog)).isEqualTo(28.80);
    }

    @Test
    public void exclude_discount_when_under_item_quantity() throws FruitUnavailableException {

        Catalog catalog = Catalog.withItems(
                new CatalogItem(Apple,15, 4.00),
                new CatalogItem(Orange, 15, 5.50),
                new CatalogItem(Banana, 15,6.00),
                new CatalogItem(Pear, 15,4.00)
        );

        Cart cart = new Cart();
        cart.add(Pear, 8);

        assertThat(cart.getTotalCost(catalog)).isEqualTo(28.80);
        cart.remove(Pear,4);
        assertThat(cart.getTotalCost(catalog)).isEqualTo(16.00);
    }
}


