package io.systeme.test_task.web;

import io.systeme.test_task.service.PricingService;

import io.systeme.test_task.validation.coupon.CouponCode;
import io.systeme.test_task.validation.product.VerifyProduct;
import io.systeme.test_task.validation.tax.TaxNumber;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * The CalculatePriceController class handles price calculation requests.
 */
@AllArgsConstructor
@RestController
public class CalculatePriceController {

    private final PricingService pricingService;

    /**
     * Calculates the total price of a product.
     *
     * @param request The price calculation request containing product, tax number, and coupon code.
     * @return The response entity with the calculated total price.
     */
    @PostMapping("/calculate-price")
    public ResponseEntity<?> calculatePrice(@RequestBody @Validated PriceRequest request) {

        double totalPrice = pricingService.calculateTotalPrice(request.product, request.taxNumber, request.couponCode);

        PriceResponse response = new PriceResponse(request, totalPrice);

        return ResponseEntity.ok(response);

    }

    public record PriceRequest(@NotNull @VerifyProduct Integer product, @NotBlank @TaxNumber String taxNumber, @CouponCode String couponCode) {}

    public record PriceResponse(PriceRequest request, Double totalPrice) {}

}
