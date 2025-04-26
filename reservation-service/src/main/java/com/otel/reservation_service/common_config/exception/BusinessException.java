package com.otel.reservation_service.common_config.exception;

public class BusinessException extends RuntimeException {

  public BusinessException() {
    super();
  }

  public BusinessException(String message) {
    super(message);
  }


  public BusinessException(String message, Throwable cause) {
    super(message, cause);
  }
}
