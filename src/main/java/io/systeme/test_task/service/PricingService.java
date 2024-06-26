package io.systeme.test_task.service;

import io.systeme.test_task.exception.BadRequestException;
import io.systeme.test_task.model.product.Coupon;
import io.systeme.test_task.model.product.Product;
import io.systeme.test_task.model.tax.Tax;
import io.systeme.test_task.repository.CouponRepository;
import io.systeme.test_task.repository.ProductRepository;
import io.systeme.test_task.repository.TaxRateRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * The PricingService class is responsible for calculating the total price of a product considering tax and coupon discounts.
 * It interacts with ProductRepository, CouponRepository, and TaxRateRepository to fetch product details, coupon information, and tax rates, respectively.
 */
@Transactional(readOnly = true)
@AllArgsConstructor
@Service
public class PricingService {

    private final ProductRepository productRepository;
    private final CouponRepository couponRepository;
    private final TaxRateRepository taxRateRepository;

    /**
     * Calculates the total price of a product with tax and coupon discounts.
     *
     * @param productId   The ID of the product.
     * @param taxNumber   The tax number for tax calculation.
     * @param couponCode  The coupon code for discount (optional).
     * @return The total price of the product after applying tax and coupon discounts.
     * @throws BadRequestException if the product or tax rate is not found.
     */
    @Transactional
    public double calculateTotalPrice(Integer productId, String taxNumber, String couponCode) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new BadRequestException("Product with id " + productId + " not found"));
        Coupon coupon = couponRepository.findByCode(couponCode);

        double price = product.getPrice();
        if (coupon != null) {
            price = applyCouponDiscount(price, coupon);
        }

        double taxRate = getTaxRateForTaxNumber(taxNumber);
        double taxAmount = price * taxRate;
        return price + taxAmount;
    }

    private double applyCouponDiscount(double price, Coupon coupon) {
        double discount = coupon.getDiscount();
        double discountedPrice;

        if (coupon.isPercentage()) {
            discountedPrice = price * (1 - discount / 100);
        } else {
            discountedPrice = price - discount;
        }

        if (discountedPrice < 0) {
            throw new BadRequestException("Total price after discount cannot be negative");
        }

        return discountedPrice;
    }

    private double getTaxRateForTaxNumber(String taxNumber) {
        String region = taxNumber.substring(0, 2);
        Tax tax = taxRateRepository.findByRegion(region);

        if (tax == null) {
            throw new BadRequestException(taxNumber);
        }
        return tax.getRate();
    }
}
