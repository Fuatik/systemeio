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

public class PurchaseControllerTest extends AbstractTest {
    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private CouponRepository couponRepository;

    @Test
    public void testPurchase_Successful() throws Exception {
        when(productRepository.findById(1)).thenReturn(java.util.Optional.of(PRODUCT));
        when(couponRepository.existsByCode("P15")).thenReturn(true);
        when(couponRepository.findByCode("P15")).thenReturn(COUPON);

        double expectedTotalPrice = (PRODUCT.getPrice() * (1 + 0.19)) * (1 - 0.15);

        perform(MockMvcRequestBuilders.post("/purchase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"product\": 1, \"taxNumber\": \"IT12345678900\", \"couponCode\": \"D15\", \"paymentProcessor\": \"paypal\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPrice").value(expectedTotalPrice));
    }
}
