package com.kousenit.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceTest {

    @InjectMocks
    private Service service;

    @Mock
    private Repository repository;

    @Test
    void getProductsThatExist() {
        when(repository.getProduct(1)).thenReturn(
                new Product(1, "Product 1", BigDecimal.valueOf(100.0)));

        List<Product> products = service.getProducts(1);
        assertAll("products",
                () -> assertEquals(1, products.size()),
                () -> assertEquals(1, products.get(0).id()),
                () -> assertEquals("Product 1", products.get(0).name()),
                () -> assertEquals(BigDecimal.valueOf(100.0), products.get(0).price()));
        verify(repository).getProduct(1);
    }

    @Test
    void getProductsThatDoNotExist() {
        when(repository.getProduct(anyInt()))
                .thenThrow(new IllegalArgumentException("Product does not exist"));

        Exception e = assertThrows(
                IllegalArgumentException.class, () -> service.getProducts(1, 2, 3));
        assertEquals("Product does not exist", e.getMessage());

        verify(repository).getProduct(anyInt());
    }

    @Test
    void saveProducts() {
        Product product1 = new Product(1, "Product 1", BigDecimal.valueOf(100));
        Product product2 = new Product(2, "Product 2", BigDecimal.valueOf(200));

        when(repository.saveProduct(product1)).thenReturn(product1);
        when(repository.saveProduct(product2)).thenReturn(product2);

        List<Product> products = service.saveProducts(product1, product2);
        assertEquals(product1, products.get(0));
        assertEquals(product2, products.get(1));

        verify(repository, times(2)).saveProduct(any(Product.class));
    }
}