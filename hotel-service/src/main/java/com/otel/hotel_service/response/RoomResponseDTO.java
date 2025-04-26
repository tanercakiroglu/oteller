package com.otel.hotel_service.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class RoomResponseDTO {

  private Long id;
  private Long hotelId;
  private String roomNumber;
  private int capacity;
  private BigDecimal pricePerNight;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
