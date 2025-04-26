package com.otel.reservation_service.service.impl;

import com.otel.reservation_service.service.ReservationValidator;
import java.time.LocalDate;
import org.springframework.stereotype.Service;

@Service
public class ReservationValidatorImpl implements ReservationValidator {

  @Override
  public void validateDates(LocalDate checkInDate, LocalDate checkOutDate) {

    // Check-in tarihinin geçmişte olup olmadığını kontrol et
    LocalDate today = LocalDate.now();
    if (checkInDate.isBefore(today)) {
      throw new IllegalArgumentException("Check in date is before today");
    }

    // Check-out tarihinin check-in tarihinden önce veya aynı olup olmadığını kontrol et
    if (!checkOutDate.isAfter(checkInDate)) {
      throw new IllegalArgumentException("Check out date is before check in date today");
    }
  }
}
