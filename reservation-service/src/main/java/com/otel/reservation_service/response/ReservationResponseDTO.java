package com.otel.reservation_service.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ReservationResponseDTO {

  private Long id;
  private Long hotelId;
  private Long roomId;
  private String guestName;
  private LocalDate checkInDate;
  private LocalDate checkOutDate;
  private LocalDateTime createdAt;
}
