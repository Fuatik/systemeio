package io.systeme.test_task.exception;

public class InvalidCouponCodeException extends RuntimeException {
    public InvalidCouponCodeException(String message) { super(message); }
}
