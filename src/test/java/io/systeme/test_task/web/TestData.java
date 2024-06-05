package io.systeme.test_task.web;

import io.systeme.test_task.model.product.Coupon;
import io.systeme.test_task.model.product.Product;
import io.systeme.test_task.model.tax.Tax;

public class TestData {
    public static final Product PRODUCT = new Product(1, "Test Product", 100);
    public static final Product PRODUCT_2 = new Product(2, "Test Product", 10);
    public static final Coupon COUPON = new Coupon("P15", 15, false);
    public static final Tax TAX_RATE = new Tax("DE", 0.19);

    public static final int PRODUCT_ID_1 = 1;
    public static final int PRODUCT_ID_2 = 2;
    public static final int PRODUCT_NOT_FOUND = 999;
    public static final String TAX_NUMBER = "DE123456789";
    public static final String INVALID_TAX_NUMBER = "INVALID123";
    public static final String COUPON_CODE = "P15";
    public static final String INVALID_COUPON_CODE = "INVALID";
    public static final String PAYMENT_PROCESSOR = "paypal";
    public static final String INVALID_PAYMENT_PROCESSOR = "INVALID";

    public static double expectedTotalPrice = calculateExpectedPrice(
            PRODUCT.getPrice(),
            TAX_RATE.getRate(),
            COUPON.getDiscount()
    );

    private static double calculateExpectedPrice(double basePrice, double taxRate, double discount) {
        return Math.round(((basePrice - discount) * (1 + taxRate)) * 100.0) / 100.0;
    }
}
