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

@AllArgsConstructor
@Service
public class PricingService {

    private final ProductRepository productRepository;
    private final CouponRepository couponRepository;
    private final TaxRateRepository taxRateRepository;

    public double calculateTotalPrice(Integer productId, String taxNumber, String couponCode) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new BadRequestException(productId.toString()));
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
        if (coupon.isPercentage()) {
            return price * (1 - discount / 100);
        } else {
            return price - discount;
        }
    }

    private double getTaxRateForTaxNumber(String taxNumber) {
        String taxRegion = taxNumber.substring(0, 2);
        Tax tax = taxRateRepository.findByRegion(taxRegion);

        if (tax == null) {
            throw new BadRequestException(taxNumber);
        }
        return tax.getRate();
    }
}
