package io.systeme.test_task.web;

import io.systeme.test_task.payment.PaymentProcessor;
import io.systeme.test_task.payment.Paypal;
import io.systeme.test_task.repository.CouponRepository;
import io.systeme.test_task.repository.ProductRepository;
import io.systeme.test_task.repository.TaxRateRepository;
import io.systeme.test_task.service.PaymentProcessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import static io.systeme.test_task.web.TestData.PAYMENT_PROCESSOR;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
public class AbstractTest {
    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    protected ProductRepository productRepository;

    @MockBean
    protected CouponRepository couponRepository;

    @MockBean
    protected TaxRateRepository taxRateRepository;

    @MockBean
    protected PaymentProcessorFactory paymentProcessorFactory;

    protected void setupMockRepositories() {
        when(productRepository.findById(TestData.PRODUCT_ID_1)).thenReturn(java.util.Optional.of(TestData.PRODUCT));
        when(productRepository.findById(TestData.PRODUCT_ID_2)).thenReturn(java.util.Optional.of(TestData.PRODUCT_2));
        when(couponRepository.findByCode(TestData.COUPON_CODE)).thenReturn(TestData.COUPON);
        when(taxRateRepository.findByRegion("DE")).thenReturn(TestData.TAX_RATE);

        PaymentProcessor mockPaypalProcessor = mock(Paypal.class);
        when(mockPaypalProcessor.payWithProcessor(anyDouble())).thenReturn(true);
        when(paymentProcessorFactory.getPaymentProcessor(PAYMENT_PROCESSOR)).thenReturn(mockPaypalProcessor);
    }

    protected ResultActions perform(MockHttpServletRequestBuilder builder) throws Exception {
        return mockMvc.perform(builder);
    }
}
