package com.otel.hotel_service.controller;

import com.otel.hotel_service.common_config.response.WrapperCollectionResponse;
import com.otel.hotel_service.common_config.response.WrapperResponse;
import com.otel.hotel_service.request.HotelRequestDTO;
import com.otel.hotel_service.response.HotelResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/hotels")
public interface HotelApi {

  @GetMapping
  WrapperCollectionResponse<HotelResponseDTO> getAllHotels();

  @GetMapping("/{id}")
  WrapperResponse<HotelResponseDTO> getHotelById(@PathVariable Long id);

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  WrapperResponse<HotelResponseDTO> createHotel(@Valid @RequestBody HotelRequestDTO hotel);

  @PutMapping("/{id}")
  WrapperResponse<HotelResponseDTO> updateHotel(@PathVariable Long id,
      @Valid @RequestBody HotelRequestDTO updatedHotel);

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  WrapperResponse<Void> deleteHotel(@PathVariable Long id);
}
