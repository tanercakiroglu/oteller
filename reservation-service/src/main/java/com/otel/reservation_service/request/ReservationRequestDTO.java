package com.otel.reservation_service.request;

import java.time.LocalDate;
import lombok.Data;

@Data
public class ReservationRequestDTO {

  private Long hotelId;
  private Long roomId;
  private String guestName;
  private LocalDate checkInDate;
  private LocalDate checkOutDate;
}
