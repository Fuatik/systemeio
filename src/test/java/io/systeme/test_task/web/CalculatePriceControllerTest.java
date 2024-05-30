package io.systeme.test_task.web;

import io.systeme.test_task.repository.CouponRepository;
import io.systeme.test_task.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static io.systeme.test_task.web.TestData.COUPON;
import static io.systeme.test_task.web.TestData.PRODUCT;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class CalculatePriceControllerTest extends AbstractTest{
    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private CouponRepository couponRepository;

    @Test
    public void testCalculatePrice_Successful() throws Exception {
        when(productRepository.findById(1)).thenReturn(java.util.Optional.of(PRODUCT));
        when(couponRepository.existsByCode("P15")).thenReturn(true);
        when(couponRepository.findByCode("P15")).thenReturn(COUPON);

        // Предположим, что налог в Германии составляет 19%
        double expectedTotalPrice = (PRODUCT.getPrice() * (1 + 0.19)) * (1 - 0.15); // Цена * (1 + налог) * (1 - скидка)

        perform(MockMvcRequestBuilders.post("/calculate-price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"product\": 1, \"taxNumber\": \"DE123456789\", \"couponCode\": \"D15\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPrice").value(expectedTotalPrice));
    }
}