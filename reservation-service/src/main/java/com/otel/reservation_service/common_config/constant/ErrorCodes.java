package com.otel.reservation_service.common_config.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ErrorCodes {

  public static final String ENTITY_NOT_FOUND = "reservation-service.entity-not-found";
  public static final String RESERVATION_SERVICE_RESERVATION_REQUEST_ALREADY_RESERVED = "reservation-service.reservation-request.already.reserved";
}
