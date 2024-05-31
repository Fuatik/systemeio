package io.systeme.test_task.web;

import io.systeme.test_task.service.PricingService;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class CalculatePriceController {

    private final PricingService pricingService;

    @PostMapping("/calculate-price")
    public ResponseEntity<?> calculatePrice(@RequestBody @Validated PriceRequest request) {

        double totalPrice = pricingService.calculateTotalPrice(request.productId, request.taxNumber, request.couponCode);

        PriceResponse response = new PriceResponse(request, totalPrice);

        return ResponseEntity.ok(response);

    }

    public record PriceRequest(@NotNull Integer productId, @NotBlank String taxNumber, String couponCode) {}

    public record PriceResponse(PriceRequest request, Double totalPrice) {}

}
