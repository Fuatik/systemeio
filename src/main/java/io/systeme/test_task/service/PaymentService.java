package io.systeme.test_task.service;

import io.systeme.test_task.exception.BadRequestException;
import io.systeme.test_task.payment.PaymentProcessor;
import io.systeme.test_task.payment.StripePaymentProcessor;
import io.systeme.test_task.payment.Paypal;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The PaymentService class handles payments using different payment processors.
 */
@Transactional(readOnly = true)
@Service
public class PaymentService {

    private PaymentProcessor paymentProcessor;

    /**
     * Constructs a PaymentService with the specified PaymentProcessor.
     *
     * @param paymentProcessor The payment processor to be used.
     */
    public PaymentService(@Qualifier("paypal") PaymentProcessor paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
    }

    /**
     * Processes payment using the specified payment processor.
     *
     * @param totalPrice      The total price to be paid.
     * @param processorsName  The name of the payment processor.
     * @throws BadRequestException if the payment fails.
     */
    @Transactional
    public void payWithProcessor(double totalPrice, String processorsName) {
        this.paymentProcessor = getPaymentProcessor(processorsName);

        if (!paymentProcessor.payWithProcessor(totalPrice)) throw new BadRequestException("Payment failed");
    }

    private PaymentProcessor getPaymentProcessor(String paymentProcessor) {
        return switch (paymentProcessor.toLowerCase()) {
            case "paypal" -> new Paypal();
            case "stripe" -> new StripePaymentProcessor();
            default -> throw new BadRequestException("Payment processor " + paymentProcessor + " not found");
        };
    }
}
