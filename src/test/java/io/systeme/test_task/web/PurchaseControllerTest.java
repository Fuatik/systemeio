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
                PRODUCT_ID, TAX_NUMBER, COUPON_CODE, PAYMENT_PROCESSOR
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

        // Создание запроса на покупку с несуществующим платежным процессором
        String purchaseBadRequest = String.format(
                "{\"productId\": %d, \"taxNumber\": \"%s\", \"couponCode\": \"%s\", \"paymentProcessor\": \"%s\"}",
                PRODUCT_ID, TAX_NUMBER, COUPON_CODE, INVALID_PAYMENT_PROCESSOR
        );

        // Ожидаем, что при попытке покупки с несуществующим платежным процессором будет возвращен статус 400
        perform(MockMvcRequestBuilders.post("/purchase")
                .contentType(MediaType.APPLICATION_JSON)
                .content(purchaseBadRequest))
                .andExpect(status().isBadRequest());
    }
}
