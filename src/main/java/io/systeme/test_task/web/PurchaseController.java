package io.systeme.test_task.web;

import io.systeme.test_task.service.PaymentService;
import io.systeme.test_task.service.PricingService;
import io.systeme.test_task.validation.coupon.CouponCode;
import io.systeme.test_task.validation.payment.VerifyPaymentProcessor;
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
 * The PurchaseController class handles purchase requests.
 */
@AllArgsConstructor
@RestController
public class PurchaseController {

    private final PricingService pricingService;
    private final PaymentService paymentService;

    /**
     * Processes a purchase request.
     *
     * @param request The purchase request containing product, tax number, coupon code, and payment processor.
     * @return The response entity with the purchase details.
     */
    @PostMapping("/purchase")
    public ResponseEntity<?> purchase(@RequestBody @Validated PurchaseRequest request) {

        double totalPrice = pricingService.calculateTotalPrice(request.product, request.taxNumber, request.couponCode);

        paymentService.payWithProcessor(totalPrice, request.paymentProcessor);

        PurchaseResponse response = new PurchaseResponse(request, totalPrice, "Payment successful");

        return ResponseEntity.ok(response);
    }

    public record PurchaseRequest (@NotNull @VerifyProduct Integer product,
                                   @NotBlank @TaxNumber String taxNumber,
                                   @CouponCode String couponCode,
                                   @NotBlank @VerifyPaymentProcessor String paymentProcessor) {}
    public record PurchaseResponse (PurchaseRequest request,
                                    Double totalPrice,
                                    String message) {}
}
