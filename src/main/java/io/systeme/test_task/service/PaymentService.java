package io.systeme.test_task.service;

import io.systeme.test_task.exception.NotFoundException;
import io.systeme.test_task.exception.PaymentException;
import io.systeme.test_task.payment.PaymentProcessor;
import io.systeme.test_task.payment.StripePaymentProcessor;
import io.systeme.test_task.payment.Paypal;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    public void payWithProcessor(double totalPrice, String paymentProcessor) {
        PaymentProcessor processor = getPaymentProcessor(paymentProcessor);

        if (!processor.payWithProcessor(totalPrice)) throw new PaymentException("Payment failed");
    }

    private PaymentProcessor getPaymentProcessor(String paymentProcessor) {
        return switch (paymentProcessor.toLowerCase()) {
            case "paypal" -> new Paypal();
            case "stripe" -> new StripePaymentProcessor();
            default -> throw new NotFoundException("Payment processor " + paymentProcessor + " not found");
        };
    }
}
