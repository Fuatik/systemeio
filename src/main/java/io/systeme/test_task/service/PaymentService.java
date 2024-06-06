package io.systeme.test_task.service;

import io.systeme.test_task.exception.BadRequestException;
import io.systeme.test_task.payment.PaymentProcessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The PaymentService class handles payments using different payment processors.
 */
@Transactional(readOnly = true)
@Service
public class PaymentService {

    private final PaymentProcessorFactory paymentProcessorFactory;

    /**
     * Constructs a PaymentService with the specified PaymentProcessorFactory.
     *
     * @param paymentProcessorFactory returns the payment processor to be used.
     */
    public PaymentService(PaymentProcessorFactory paymentProcessorFactory) {
        this.paymentProcessorFactory = paymentProcessorFactory;
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
        PaymentProcessor processor = paymentProcessorFactory.getPaymentProcessor(processorsName);

        if (!processor.payWithProcessor(totalPrice)) {
            throw new BadRequestException("Payment failed");
        }
    }
}
