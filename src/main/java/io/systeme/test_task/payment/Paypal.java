package io.systeme.test_task.payment;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class Paypal implements PaymentProcessor {

    private void makePayment(Integer price) throws Exception {
        if (price > 100000) {
            throw new Exception("Price exceeds the Paypal limit.");
        }
    }

    @Override
    public boolean payWithProcessor(double price) {
        try {
            makePayment((int) price);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String name() {
        return "paypal";
    }
}
