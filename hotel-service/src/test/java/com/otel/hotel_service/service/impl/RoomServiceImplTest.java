package com.otel.hotel_service.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.otel.hotel_service.entity.Room;
import com.otel.hotel_service.mapper.RoomMapperImpl;
import com.otel.hotel_service.repository.RoomRepository;
import com.otel.hotel_service.request.RoomPutRequestDTO;
import com.otel.hotel_service.response.RoomResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

class RoomServiceImplTest {

  @Mock
  private RoomRepository roomRepository;

  @InjectMocks
  private RoomServiceImpl roomService;

  @Spy
  RoomMapperImpl roomMapper;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getAllRooms_ShouldReturnListOfRooms() {
    List<Room> rooms = new ArrayList<>();
    rooms.add(new Room());
    rooms.add(new Room());
    when(roomRepository.findAll()).thenReturn(rooms);

    List<RoomResponseDTO> result = roomService.getAllRooms();

    assertEquals(2, result.size());
  }

  @Test
  void getRoomById_ShouldReturnRoom_WhenRoomExists() {

    Room room = new Room();
    room.setId(1L);
    when(roomRepository.findById(1L)).thenReturn(Optional.of(room));

    RoomResponseDTO result = roomService.getRoomById(1L);

    assertNotNull(result);
    assertEquals(1L, result.getId());
  }

  @Test
  void getRoomById_ShouldReturnNotFound_WhenRoomDoesNotExist() {
    when(roomRepository.findById(1L)).thenReturn(Optional.empty());
    assertThrows(EntityNotFoundException.class,()-> roomService.getRoomById(1L));
  }

  @Test
  void shouldUpdateRoom() {

    Room existingRoom = new Room();
    existingRoom.setId(1L);
    existingRoom.setCapacity(1);
    existingRoom.setPricePerNight(BigDecimal.TEN);

    Room newRoom = new Room();
    newRoom.setCapacity(2);
    newRoom.setRoomNumber("New room number");
    newRoom.setPricePerNight(BigDecimal.ONE);

    RoomPutRequestDTO updatedRoom = new RoomPutRequestDTO();
    updatedRoom.setCapacity(2);
    updatedRoom.setRoomNumber("New room number");
    updatedRoom.setPricePerNight(BigDecimal.ONE);

    when(roomRepository.findById(1L)).thenReturn(Optional.of(existingRoom));
    when(roomRepository.save(any(Room.class))).thenReturn(newRoom);

    RoomResponseDTO result = roomService.update(1L, updatedRoom);

    verify(roomRepository, times(1)).findById(1L);
    verify(roomRepository, times(1)).save(existingRoom);
    assertThat(result.getRoomNumber()).isEqualTo("New room number");
    assertThat(result.getCapacity()).isEqualTo(2);
  }

  @Test
  void shouldThrowExceptionWhenUpdatingNonExistingRoom() {
    when(roomRepository.findById(1L)).thenReturn(Optional.empty());

    RoomPutRequestDTO updatedRoom = new RoomPutRequestDTO();
    updatedRoom.setCapacity(2);
    updatedRoom.setRoomNumber("New Address");
    updatedRoom.setPricePerNight(BigDecimal.ONE);
    assertThatThrownBy(() -> roomService.update(1L, updatedRoom))
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessageContaining("1");
  }

  @Test
  void shouldDeleteRoom() {
    when(roomRepository.findById(1L)).thenReturn(Optional.of(new Room()));
    roomService.delete(1L);
    verify(roomRepository, times(1)).deleteById(1L);

  }

  @Test
  void shouldThrowExceptionWhenDeletingNonExistingRoom() {
    when(roomRepository.existsById(1L)).thenReturn(false);

    assertThatThrownBy(() -> roomService.delete(1L))
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessageContaining("1");
  }
}