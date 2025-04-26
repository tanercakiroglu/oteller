package com.otel.hotel_service.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class RoomPutRequestDTO {

  @NotEmpty(message = "hotel-service.room-request.invalid.room-number")
  private String roomNumber;
  @Min(value = 1,message = "hotel-service.room-request.invalid.room-capacity")
  private int capacity;
  @NotNull(message = "hotel-service.room-request.invalid.room-price")
  @DecimalMin(value = "0.0",message = "hotel-service.room-request.invalid.room-price")
  private BigDecimal pricePerNight;
}
