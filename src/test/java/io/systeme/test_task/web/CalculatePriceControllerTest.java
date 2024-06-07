package io.systeme.test_task.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static io.systeme.test_task.web.TestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class CalculatePriceControllerTest extends AbstractTest{

    @Test
    public void testCalculatePrice_Successful() throws Exception {

        setupMockRepositories();

        String priceRequest = String.format(
                "{\"product\": %d, \"taxNumber\": \"%s\", \"couponCode\": \"%s\"}",
                PRODUCT_ID_1, TAX_NUMBER, COUPON_CODE
        );

        perform(MockMvcRequestBuilders.post("/calculate-price")
                .contentType(MediaType.APPLICATION_JSON)
                .content(priceRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPrice").value(expectedTotalPrice));
    }

    @Test
    public void testCalculatePrice_InvalidTaxNumber() throws Exception {
        setupMockRepositories();

        String priceBadRequest = String.format(
                "{\"product\": %d, \"taxNumber\": \"%s\", \"couponCode\": \"%s\"}",
                PRODUCT_ID_1, INVALID_TAX_NUMBER, COUPON_CODE
        );

        perform(MockMvcRequestBuilders.post("/calculate-price")
                .contentType(MediaType.APPLICATION_JSON)
                .content(priceBadRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.type").value("about:blank"))
                .andExpect(jsonPath("$.title").value("Bad Request"))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.detail").value("Invalid tax number"))
                .andExpect(jsonPath("$.instance").value("/calculate-price"));
    }

    @Test
    public void testCalculatePrice_InvalidCouponCode() throws Exception {
        setupMockRepositories();

        String priceBadRequest = String.format(
                "{\"product\": %d, \"taxNumber\": \"%s\", \"couponCode\": \"%s\"}",
                PRODUCT_ID_1, TAX_NUMBER, INVALID_COUPON_CODE
        );

        perform(MockMvcRequestBuilders.post("/calculate-price")
                .contentType(MediaType.APPLICATION_JSON)
                .content(priceBadRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.type").value("about:blank"))
                .andExpect(jsonPath("$.title").value("Bad Request"))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.detail").value("Invalid coupon code"))
                .andExpect(jsonPath("$.instance").value("/calculate-price"));
    }

    @Test
    public void testCalculatePrice_InvalidProductId() throws Exception {
        setupMockRepositories();

        String priceBadRequest = String.format(
                "{\"product\": %d, \"taxNumber\": \"%s\", \"couponCode\": \"%s\"}",
                PRODUCT_NOT_FOUND, TAX_NUMBER, COUPON_CODE
        );

        perform(MockMvcRequestBuilders.post("/calculate-price")
                .contentType(MediaType.APPLICATION_JSON)
                .content(priceBadRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.type").value("about:blank"))
                .andExpect(jsonPath("$.title").value("Bad Request"))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.detail").value("Product with id 999 not found"))
                .andExpect(jsonPath("$.instance").value("/calculate-price"));
    }

    @Test
    public void testCalculatePrice_InvalidProductJsonFormat() throws Exception {
        setupMockRepositories();

        String purchaseRequest = String.format(
                "{\"product\": \"%s\", \"taxNumber\": \"%s\", \"couponCode\": \"%s\", \"paymentProcessor\": \"%s\"}",
                "invalid_product", TAX_NUMBER, COUPON_CODE, PAYMENT_PROCESSOR
        );

        perform(MockMvcRequestBuilders.post("/calculate-price")
                .contentType(MediaType.APPLICATION_JSON)
                .content(purchaseRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.type").value("about:blank"))
                .andExpect(jsonPath("$.title").value("Bad Request"))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.detail").value("JSON parse error: Cannot deserialize value of type `java.lang.Integer` from String \"invalid_product\": not a valid `java.lang.Integer` value"))
                .andExpect(jsonPath("$.instance").value("/calculate-price"));
    }

    @Test
    public void testCalculatePrice_NegativePriceAfterCoupon() throws Exception {
        setupMockRepositories();

        String priceRequest = String.format(
                "{\"product\": %d, \"taxNumber\": \"%s\", \"couponCode\": \"%s\"}",
                PRODUCT_ID_2, TAX_NUMBER, COUPON_CODE
        );

        perform(MockMvcRequestBuilders.post("/calculate-price")
                .contentType(MediaType.APPLICATION_JSON)
                .content(priceRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.type").value("about:blank"))
                .andExpect(jsonPath("$.title").value("Bad Request"))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.detail").value("Total price after discount cannot be negative"))
                .andExpect(jsonPath("$.instance").value("/calculate-price"));
    }
}