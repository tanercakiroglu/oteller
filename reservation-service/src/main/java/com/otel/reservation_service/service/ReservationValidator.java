package com.otel.reservation_service.service;

import java.time.LocalDate;

public interface ReservationValidator {

  void validateDates(LocalDate checkInDate, LocalDate checkOutDate);
}
