package io.systeme.test_task.web

import io.systeme.test_task.service.PricingService
import io.systeme.test_task.validation.coupon.CouponCode
import io.systeme.test_task.validation.tax.TaxNumber
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class KotlinCalculatePriceController(private val pricingService: PricingService) {

    @PostMapping("/calculate-price")
    fun calculatePrice(@RequestBody @Validated request: PriceRequest): ResponseEntity<*> {
        val totalPrice = pricingService.calculateTotalPrice(request.product, request.taxNumber, request.couponCode)
        val response = PriceResponse(request, totalPrice)
        return ResponseEntity.ok(response)
    }

    data class PriceRequest(
        @field:NotNull val product: Int,
        @field:NotBlank @field:TaxNumber val taxNumber: String,
        @field:CouponCode val couponCode: String?
    )

    data class PriceResponse(val request: PriceRequest, val totalPrice: Double)
}
