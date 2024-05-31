package io.systeme.test_task.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static io.systeme.test_task.web.TestData.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class PurchaseControllerTest extends AbstractTest {

    @Test
    public void testPurchase_Successful() throws Exception {
        when(productRepository.findById(1)).thenReturn(java.util.Optional.of(PRODUCT));
        when(couponRepository.findByCode("P15")).thenReturn(COUPON);
        when(taxRateRepository.findByRegion("DE")).thenReturn(TAX_RATE);

        double expectedTotalPrice = calculateExpectedPrice(PRODUCT.getPrice(), TAX_RATE.getRate(), COUPON.getDiscount());

        String purchaseRequest = "{\"productId\": " + PRODUCT_ID + ", " +
                "\"taxNumber\": \"" + TAX_NUMBER + "\", " +
                "\"couponCode\": \"" + COUPON_CODE + "\", " +
                "\"paymentProcessor\": \"" + PAYMENT_PROCESSOR + "\"}";

        perform(MockMvcRequestBuilders.post("/purchase")
                .contentType(MediaType.APPLICATION_JSON)
                .content(purchaseRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPrice").value(expectedTotalPrice))
                .andExpect(jsonPath("$.message").value("Payment successful"));
    }

    private double calculateExpectedPrice(double basePrice, double taxRate, double discount) {
        return Math.round((basePrice * (1 + taxRate)) * (1 - discount / 100) * 100.0) / 100.0;
    }
}
