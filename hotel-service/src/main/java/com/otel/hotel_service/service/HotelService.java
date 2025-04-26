package com.otel.hotel_service.service;

import com.otel.hotel_service.request.HotelRequestDTO;
import com.otel.hotel_service.response.HotelResponseDTO;
import java.util.List;

public interface HotelService {

  List<HotelResponseDTO> getAllHotels();

  HotelResponseDTO getHotelById(Long id);

  HotelResponseDTO create(HotelRequestDTO hotel);

  HotelResponseDTO update(Long id, HotelRequestDTO updatedHotel);

  void delete(Long id);
}
