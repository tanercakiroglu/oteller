package com.otel.hotel_service.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.otel.hotel_service.entity.Hotel;
import com.otel.hotel_service.mapper.HotelMapperImpl;
import com.otel.hotel_service.repository.HotelRepository;
import com.otel.hotel_service.repository.RoomRepository;
import com.otel.hotel_service.request.HotelRequestDTO;
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

  @Mock
  private RoomRepository roomRepository;

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
    List<Hotel> hotels = new ArrayList<>();
    hotels.add(new Hotel());
    hotels.add(new Hotel());
    when(hotelRepository.findAll()).thenReturn(hotels);

    List<HotelResponseDTO> result = hotelService.getAllHotels();

    assertEquals(2, result.size());
  }

  @Test
  void getHotelById_ShouldReturnHotel_WhenHotelExists() {

    Hotel hotel = new Hotel();
    hotel.setId(1L);
    when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));

    HotelResponseDTO result = hotelService.getHotelById(1L);

    assertNotNull(result);
    assertEquals(1L, result.getId());
  }

  @Test
  void getHotelById_ShouldReturnNotFound_WhenHotelDoesNotExist() {
    when(hotelRepository.findById(1L)).thenReturn(Optional.empty());
    assertThrows(EntityNotFoundException.class,()-> hotelService.getHotelById(1L));
  }

  @Test
  void shouldUpdateHotel() {

    Hotel existingHotel = new Hotel();
    existingHotel.setId(1L);
    existingHotel.setName("Old Hotel Name");
    existingHotel.setAddress("Old Address");

    Hotel newHotel = new Hotel();
    newHotel.setId(1L);
    newHotel.setName("New Hotel Name");
    newHotel.setAddress("New Address");

    HotelRequestDTO updatedHotel = new HotelRequestDTO();
    updatedHotel.setName("New Hotel Name");
    updatedHotel.setAddress("New Address");

    when(hotelRepository.findById(1L)).thenReturn(Optional.of(existingHotel));
    when(hotelRepository.save(any(Hotel.class))).thenReturn(newHotel);

    HotelResponseDTO result = hotelService.update(1L, updatedHotel);

    verify(hotelRepository, times(1)).findById(1L);
    verify(hotelRepository, times(1)).save(existingHotel);
    assertThat(result.getName()).isEqualTo("New Hotel Name");
    assertThat(result.getAddress()).isEqualTo("New Address");
  }

  @Test
  void shouldThrowExceptionWhenUpdatingNonExistingHotel() {
    when(hotelRepository.findById(1L)).thenReturn(Optional.empty());

    HotelRequestDTO updatedHotel = new HotelRequestDTO();
    updatedHotel.setName("New Hotel Name");
    updatedHotel.setAddress("New Address");
    assertThatThrownBy(() -> hotelService.update(1L, updatedHotel))
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessageContaining("1");
  }

  @Test
  void shouldDeleteHotel() {
    when(hotelRepository.findById(1L)).thenReturn(Optional.of(new Hotel()));
    hotelService.delete(1L);
    verify(hotelRepository, times(1)).deleteById(1L);
    verify(roomRepository, times(1)).deleteByHotelId(anyLong());
  }

  @Test
  void shouldThrowExceptionWhenDeletingNonExistingHotel() {
    when(hotelRepository.findById(1L)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> hotelService.delete(1L))
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessageContaining("1");
  }


}