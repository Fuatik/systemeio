package io.systeme.test_task.payment;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class StripePaymentProcessor implements PaymentProcessor {

    private boolean pay(Float price) {
        return price >= 100;
    }

    @Override
    public boolean payWithProcessor(double price) {
        return pay((float) price);
    }

    @Override
    public String name() {
        return "stripe";
    }
}
