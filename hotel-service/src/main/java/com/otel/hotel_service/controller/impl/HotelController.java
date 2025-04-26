package com.otel.hotel_service.controller.impl;

import static org.springframework.http.HttpStatus.CREATED;

import com.otel.hotel_service.common_config.Loggable;
import com.otel.hotel_service.common_config.response.WrapperCollectionResponse;
import com.otel.hotel_service.common_config.response.WrapperResponse;
import com.otel.hotel_service.controller.HotelApi;
import com.otel.hotel_service.request.HotelRequestDTO;
import com.otel.hotel_service.response.HotelResponseDTO;
import com.otel.hotel_service.service.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Loggable
public class HotelController implements HotelApi {

  private final HotelService hotelService;

  @Override
  public WrapperCollectionResponse<HotelResponseDTO> getAllHotels() {
    return WrapperCollectionResponse.of(hotelService.getAllHotels());
  }

  @Override
  public WrapperResponse<HotelResponseDTO> getHotelById(Long id) {
    return WrapperResponse.of(hotelService.getHotelById(id));
  }

  @Override
  public WrapperResponse<HotelResponseDTO> createHotel(@Valid @RequestBody HotelRequestDTO hotel) {
    WrapperResponse<HotelResponseDTO> created = WrapperResponse.of(hotelService.create(hotel));
    created.setStatus(CREATED.value());
    return created;
  }

  @Override
  public WrapperResponse<HotelResponseDTO> updateHotel(Long id,
      @Valid HotelRequestDTO updatedHotel) {
    return WrapperResponse.of(hotelService.update(id, updatedHotel));
  }

  @Override
  public WrapperResponse<Void> deleteHotel(Long id) {
    hotelService.delete(id);
    return WrapperResponse.empty();
  }
}
