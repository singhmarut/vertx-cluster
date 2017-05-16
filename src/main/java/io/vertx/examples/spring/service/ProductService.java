package io.vertx.examples.spring.service;

import io.vertx.examples.spring.entity.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple Spring service bean to expose the results of a trivial database call
 */
public class ProductService {

    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        Product dummyProduct = new Product(1,"Car");
        return productList;
    }
}
