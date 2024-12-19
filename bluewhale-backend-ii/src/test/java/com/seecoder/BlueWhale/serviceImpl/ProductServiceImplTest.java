package com.seecoder.BlueWhale.serviceImpl;

import com.seecoder.BlueWhale.po.Product;
import com.seecoder.BlueWhale.repository.CouponRepository;
import com.seecoder.BlueWhale.repository.ProductRepository;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class ProductServiceImplTest {
    @InjectMocks
    ProductServiceImpl productService;

    @Mock
    ProductRepository productRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void changeInventory() {
        Product mockProduct = new Product();
        mockProduct.setId(1000);
        mockProduct.setName("mockProduct");
        mockProduct.setQuantity(10);
        when(productRepository.getProductById(any())).thenReturn(mockProduct);

        Assert.assertEquals(true,productService.changeInventory(1000,10));

    }
}