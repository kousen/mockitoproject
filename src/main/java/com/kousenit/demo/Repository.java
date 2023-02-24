package com.kousenit.demo;

public interface Repository {
    Product getProduct(Integer id);
    Product saveProduct(Product product);
}
