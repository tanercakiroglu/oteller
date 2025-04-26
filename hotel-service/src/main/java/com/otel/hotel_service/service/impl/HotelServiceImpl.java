package com.otel.hotel_service.service.impl;

import com.otel.hotel_service.entity.Hotel;
import com.otel.hotel_service.mapper.HotelMapper;
import com.otel.hotel_service.repository.HotelRepository;
import com.otel.hotel_service.repository.RoomRepository;
import com.otel.hotel_service.request.HotelRequestDTO;
import com.otel.hotel_service.response.HotelResponseDTO;
import com.otel.hotel_service.service.HotelService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

  private final HotelRepository hotelRepository;
  private final HotelMapper hotelMapper;
  private final RoomRepository roomRepository;

  @Override
  public List<HotelResponseDTO> getAllHotels() {
    return hotelMapper.map(hotelRepository.findAll());
  }

  @Override
  public HotelResponseDTO getHotelById(Long id) {
    return hotelRepository.findById(id)
        .map(hotelMapper::map)
        .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
  }

  @Override
  public HotelResponseDTO create(HotelRequestDTO hotel) {
    Hotel toBeSaved = hotelMapper.map(hotel);
    return hotelMapper.map(hotelRepository.save(toBeSaved));
  }

  @Override
  public HotelResponseDTO update(Long id, HotelRequestDTO updatedHotel) {
    return hotelRepository.findById(id)
        .map(hotel -> {
          hotel.setName(updatedHotel.getName());
          hotel.setAddress(updatedHotel.getAddress());
          hotel.setStarRating(updatedHotel.getStarRating());
          Hotel savedHotel = hotelRepository.save(hotel);
          return hotelMapper.map(savedHotel);
        })
        .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
  }

  @Override
  @Transactional
  public void delete(Long id) {
    hotelRepository.findById(id)
        .map(hotel -> {
          hotelRepository.deleteById(id);
          roomRepository.deleteByHotelId(id);
          return hotel;
        })
        .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
  }
}
