package com.otel.hotel_service.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class HotelRequestDTO {

  @NotEmpty(message = "hotel-service.hotel-request.invalid.name")
  private String name;
  @NotEmpty(message = "hotel-service.hotel-request.invalid.address")
  private String address;
  @Min(value = 1, message = "hotel-service.hotel-request.invalid.star-rating")
  private int starRating;
}
