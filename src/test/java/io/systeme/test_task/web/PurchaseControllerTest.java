package io.systeme.test_task.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static io.systeme.test_task.web.TestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class PurchaseControllerTest extends AbstractTest {

    @Test
    public void testPurchase_Successful() throws Exception {

        setupMockRepositories();

        String purchaseRequest = String.format(
                "{\"productId\": %d, \"taxNumber\": \"%s\", \"couponCode\": \"%s\", \"paymentProcessor\": \"%s\"}",
                TestData.PRODUCT_ID, TestData.TAX_NUMBER, TestData.COUPON_CODE, TestData.PAYMENT_PROCESSOR
        );

        perform(MockMvcRequestBuilders.post("/purchase")
                .contentType(MediaType.APPLICATION_JSON)
                .content(purchaseRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPrice").value(expectedTotalPrice))
                .andExpect(jsonPath("$.message").value("Payment successful"));
    }
}
