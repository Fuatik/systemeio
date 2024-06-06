package io.systeme.test_task.service;

import io.systeme.test_task.payment.PaymentProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaymentProcessorFactory {
    private final Map<String, PaymentProcessor> processors = new HashMap<>();

    @Autowired
    public PaymentProcessorFactory(List<PaymentProcessor> processorList) {
        for (PaymentProcessor processor : processorList) {
            processors.put(processor.name(), processor);
        }
    }

    public PaymentProcessor getPaymentProcessor(String name) {
        return processors.get(name.toLowerCase());
    }
}
