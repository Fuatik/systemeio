package io.systeme.test_task.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.systeme.test_task.repository.CouponRepository;
import io.systeme.test_task.repository.ProductRepository;
import io.systeme.test_task.repository.TaxRateRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static io.systeme.test_task.web.TestData.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class CalculatePriceControllerTest extends AbstractTest{
    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private CouponRepository couponRepository;

    @MockBean
    private TaxRateRepository taxRateRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCalculatePrice_Successful() throws Exception {
        CalculatePriceController.PriceRequest request = new CalculatePriceController.PriceRequest(1, "DE123456789", "P15");

        when(productRepository.findById(1)).thenReturn(java.util.Optional.of(PRODUCT));
        when(couponRepository.existsByCode("P15")).thenReturn(true);
        when(couponRepository.findByCode("P15")).thenReturn(COUPON);
        when(taxRateRepository.findByTaxRegion("DE")).thenReturn(TAX_RATE);

        double expectedTotalPrice = Math.round((PRODUCT.getPrice() * (1 + 0.19)) * (1 - 0.15) * 100.0) / 100.0; // price * (1 + tax) * (1 - discount)

        perform(MockMvcRequestBuilders.post("/calculate-price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPrice").value(expectedTotalPrice));
    }
}