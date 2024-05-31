package io.systeme.test_task.exception;

public class BadRequestException extends RuntimeException {
   public BadRequestException(String message) { super(message); }
}
