package io.systeme.test_task.payment;

public class StripePaymentProcessor implements PaymentProcessor {

    private boolean pay (Float price) {
        return price >= 100;
    }

    @Override
    public boolean payWithProcessor(double price) {
        return pay((float) price);
    }
}
