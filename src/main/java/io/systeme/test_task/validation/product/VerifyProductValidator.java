package io.systeme.test_task.validation.product;

import io.systeme.test_task.repository.ProductRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class VerifyProductValidator implements ConstraintValidator<VerifyProduct, Integer> {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public boolean isValid(Integer productId, ConstraintValidatorContext var) {
        if (productId == null) {
            return false; // Allow null values, use @NotNull for non-null validation
        }
        if (productRepository.findById(productId).isEmpty()) {
            var.disableDefaultConstraintViolation();
            var.buildConstraintViolationWithTemplate("Product with id " + productId + " not found").addConstraintViolation();
            return false;
        }
        return true;
    }
}
