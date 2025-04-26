package com.otel.reservation_service.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Data;

@Data
public class ReservationRequestDTO {

  @NotNull(message = "reservation-service.reservation-request.invalid.hotel-id")
  private Long hotelId;
  @NotNull(message = "reservation-service.reservation-request.invalid.room-id")
  private Long roomId;
  @NotEmpty(message = "reservation-service.reservation-request.invalid.guest-name")
  private String guestName;
  @NotNull(message = "reservation-service.reservation-request.invalid.checkin-date")
  private LocalDate checkInDate;
  @NotNull(message = "reservation-service.reservation-request.invalid.checkout-date")
  private LocalDate checkOutDate;
}
