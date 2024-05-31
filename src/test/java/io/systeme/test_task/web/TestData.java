package io.systeme.test_task.web;

import io.systeme.test_task.model.product.Coupon;
import io.systeme.test_task.model.product.Product;
import io.systeme.test_task.model.tax.Tax;

public class TestData {
    public static final Product PRODUCT = new Product(1, "Test Product", 100);
    public static final Coupon COUPON = new Coupon("P15", 15, true);
    public static final Tax TAX_RATE = new Tax("DE", 0.19);

    public static final int PRODUCT_ID = 1;
    public static final String TAX_NUMBER = "DE123456789";
    public static final String COUPON_CODE = "P15";
}
