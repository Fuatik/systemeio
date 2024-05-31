package io.systeme.test_task.web;

import io.systeme.test_task.exception.InvalidTaxNumberException;
import io.systeme.test_task.exception.NotFoundException;
import io.systeme.test_task.service.PaymentService;
import io.systeme.test_task.service.PricingService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class PurchaseController {

    private final PricingService pricingService;

    private final PaymentService paymentService;

    @PostMapping("/purchase")
    public ResponseEntity<?> purchase(@RequestBody @Validated PurchaseRequest request) {
        try {
            double totalPrice = pricingService.calculateTotalPrice(request.productId, request.taxNumber, request.couponCode);

            paymentService.payWithProcessor(totalPrice, request.paymentProcessor);

            PurchaseResponse response = new PurchaseResponse(request, totalPrice, "Payment successful");

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

    public record PurchaseRequest (@NotNull Integer productId, @NotBlank String taxNumber, String couponCode, @NotBlank String paymentProcessor) {}
    public record PurchaseResponse (PurchaseRequest request, Double totalPrice, String message) {}
}
