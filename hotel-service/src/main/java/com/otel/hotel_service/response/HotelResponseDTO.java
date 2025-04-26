package com.otel.hotel_service.response;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class HotelResponseDTO {
  private Long id;
  private String name;
  private String address;
  private int starRating;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
