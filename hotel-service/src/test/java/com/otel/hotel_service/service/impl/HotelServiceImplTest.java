package com.otel.hotel_service.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.otel.hotel_service.entity.Hotel;
import com.otel.hotel_service.mapper.HotelMapperImpl;
import com.otel.hotel_service.repository.HotelRepository;
import com.otel.hotel_service.response.HotelResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

class HotelServiceImplTest {


  @Mock
  private HotelRepository hotelRepository;

  @InjectMocks
  private HotelServiceImpl hotelService;

  @Spy
  HotelMapperImpl hotelMapper;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getAllHotels_ShouldReturnListOfHotels() {
    // Arrange
    List<Hotel> hotels = new ArrayList<>();
    hotels.add(new Hotel());
    hotels.add(new Hotel());
    when(hotelRepository.findAll()).thenReturn(hotels);

    // Act
    List<HotelResponseDTO> result = hotelService.getAllHotels();

    // Assert
    assertEquals(2, result.size());
  }

  @Test
  void getHotelById_ShouldReturnHotel_WhenHotelExists() {
    // Arrange
    Hotel hotel = new Hotel();
    hotel.setId(1L);
    when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));

    // Act
    HotelResponseDTO result = hotelService.getHotelById(1L);

    // Assert

    assertNotNull(result);
    assertEquals(1L, result.getId());
  }

  @Test
  void getHotelById_ShouldReturnNotFound_WhenHotelDoesNotExist() {
    // Arrange
    when(hotelRepository.findById(1L)).thenReturn(Optional.empty());

    // Act
    assertThrows(EntityNotFoundException.class,()-> hotelService.getHotelById(1L));

  }

}