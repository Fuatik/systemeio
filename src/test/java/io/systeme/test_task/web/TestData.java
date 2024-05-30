package io.systeme.test_task.web;

import io.systeme.test_task.model.product.Coupon;
import io.systeme.test_task.model.product.Product;

public class TestData {
    public static final Product PRODUCT = new Product(1, "Test Product", 100);
    public static final Coupon COUPON = new Coupon("P15", 15, true);
}