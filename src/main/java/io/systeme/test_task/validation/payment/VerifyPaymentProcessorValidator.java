package io.systeme.test_task.validation.payment;

import io.systeme.test_task.service.PaymentProcessorFactory;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class VerifyPaymentProcessorValidator implements ConstraintValidator<VerifyPaymentProcessor, String> {
    @Autowired
    private PaymentProcessorFactory paymentProcessorFactory;

    @Override
    public boolean isValid(String paymentProcessor, ConstraintValidatorContext var) {
        if (paymentProcessor == null) {
            return false;
        }
        if (paymentProcessorFactory.getPaymentProcessor(paymentProcessor) == null) {
            var.disableDefaultConstraintViolation();
            var.buildConstraintViolationWithTemplate("Invalid payment processor").addConstraintViolation();
            return false;
        }
        return true;
    }
}

