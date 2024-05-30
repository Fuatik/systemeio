package io.systeme.test_task.web;

import io.systeme.test_task.exception.InvalidTaxNumberException;
import io.systeme.test_task.exception.NotFoundException;
import io.systeme.test_task.model.product.Coupon;
import io.systeme.test_task.model.product.Product;
import io.systeme.test_task.model.tax.TaxRate;
import io.systeme.test_task.repository.CouponRepository;
import io.systeme.test_task.repository.ProductRepository;
import io.systeme.test_task.repository.TaxRateRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class CalculatePriceController {

    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private final CouponRepository couponRepository;
    @Autowired
    private final TaxRateRepository taxRateRepository;


    @PostMapping("/calculate-price")
    public ResponseEntity<?> calculatePrice(@RequestBody @Validated PriceRequest request) {
        try {
            Product product = productRepository.findById(request.productId).orElseThrow(() -> new NotFoundException(request.productId.toString()));
            Coupon coupon = couponRepository.findByCode(request.couponCode);

            double price = product.getPrice();
            if (coupon != null) {
                price = applyCouponDiscount(price, coupon);
            }

            double taxRate = getTaxRateForTaxNumber(request.taxNumber);
            double taxAmount = price * taxRate;
            double totalPrice = price + taxAmount;

            PriceResponse response = new PriceResponse(request.productId, request.taxNumber, request.couponCode, totalPrice);

            return ResponseEntity.ok(response);
        } catch (InvalidTaxNumberException e) {
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                    HttpStatus.BAD_REQUEST,
                    e.getMessage());
            return ResponseEntity.badRequest().body(problemDetail);
        } catch (NotFoundException e) {
            ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                    HttpStatus.NOT_FOUND,
                    "Product with ID " + request.productId() + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
        }
    }

    public record PriceRequest(Integer productId, String taxNumber, String couponCode) {}

    public record PriceResponse(Integer productId, String taxNumber, String couponCode, Double totalPrice) {}

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
        TaxRate taxRate = taxRateRepository.findByTaxRegion(taxRegion);

        if (taxRate == null) {
            throw new InvalidTaxNumberException(taxNumber);
        }
        return taxRate.getTaxRate();
    }
}
