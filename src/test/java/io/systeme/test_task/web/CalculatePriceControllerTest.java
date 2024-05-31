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
                "{\"productId\": %d, \"taxNumber\": \"%s\", \"couponCode\": \"%s\"}",
                PRODUCT_ID, TAX_NUMBER, COUPON_CODE
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

        String badPriceRequest = String.format(
                "{\"productId\": %d, \"taxNumber\": \"%s\", \"couponCode\": \"%s\"}",
                PRODUCT_ID, INVALID_TAX_NUMBER, COUPON_CODE
        );

        perform(MockMvcRequestBuilders.post("/calculate-price")
                .contentType(MediaType.APPLICATION_JSON)
                .content(badPriceRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCalculatePrice_InvalidCouponCode() throws Exception {
        setupMockRepositories();

        String badPriceRequest = String.format(
                "{\"productId\": %d, \"taxNumber\": \"%s\", \"couponCode\": \"%s\"}",
                PRODUCT_ID, TAX_NUMBER, INVALID_COUPON_CODE
        );

        perform(MockMvcRequestBuilders.post("/calculate-price")
                .contentType(MediaType.APPLICATION_JSON)
                .content(badPriceRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCalculatePrice_InvalidProductId() throws Exception {
        setupMockRepositories();

        String badPriceRequest = String.format(
                "{\"productId\": %d, \"taxNumber\": \"%s\", \"couponCode\": \"%s\"}",
                PRODUCT_NOT_FOUND, TAX_NUMBER, COUPON_CODE
        );

        perform(MockMvcRequestBuilders.post("/calculate-price")
                .contentType(MediaType.APPLICATION_JSON)
                .content(badPriceRequest))
                .andExpect(status().isNotFound());
    }
}