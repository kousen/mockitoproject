package com.kousenit.demo;

import java.util.Arrays;
import java.util.List;

public class Service {
    private final Repository repository;

    public Service(Repository repository) {
        this.repository = repository;
    }

    public List<Product> getProducts(Integer... ids) {
        return Arrays.stream(ids)
                .map(repository::getProduct)
                .toList();
    }

    public List<Product> saveProducts(Product... products) {
        return Arrays.stream(products)
                .map(repository::saveProduct)
                .toList();
    }
}
