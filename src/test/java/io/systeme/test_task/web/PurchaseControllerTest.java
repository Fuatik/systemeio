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
                "{\"product\": %d, \"taxNumber\": \"%s\", \"couponCode\": \"%s\", \"paymentProcessor\": \"%s\"}",
                PRODUCT_ID_1, TAX_NUMBER, COUPON_CODE, PAYMENT_PROCESSOR
        );

        perform(MockMvcRequestBuilders.post("/purchase")
                .contentType(MediaType.APPLICATION_JSON)
                .content(purchaseRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPrice").value(expectedTotalPrice))
                .andExpect(jsonPath("$.message").value("Payment successful"));
    }

    @Test
    public void testPurchase_Unsuccessful_NonExistingPaymentProcessor() throws Exception {
        setupMockRepositories();

        String purchaseBadRequest = String.format(
                "{\"product\": %d, \"taxNumber\": \"%s\", \"couponCode\": \"%s\", \"paymentProcessor\": \"%s\"}",
                PRODUCT_ID_1, TAX_NUMBER, COUPON_CODE, INVALID_PAYMENT_PROCESSOR
        );

        perform(MockMvcRequestBuilders.post("/purchase")
                .contentType(MediaType.APPLICATION_JSON)
                .content(purchaseBadRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.type").value("about:blank"))
                .andExpect(jsonPath("$.title").value("Bad Request"))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.detail").value("Payment processor INVALID not found"))
                .andExpect(jsonPath("$.instance").value("/purchase"));
    }
}
