package com.otel.hotel_service.service.impl;

import com.otel.hotel_service.entity.Room;
import com.otel.hotel_service.mapper.RoomMapper;
import com.otel.hotel_service.repository.RoomRepository;
import com.otel.hotel_service.request.RoomPostRequestDTO;
import com.otel.hotel_service.request.RoomPutRequestDTO;
import com.otel.hotel_service.response.RoomResponseDTO;
import com.otel.hotel_service.service.HotelService;
import com.otel.hotel_service.service.RoomService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

  private final RoomRepository roomRepository;
  private final RoomMapper roomMapper;
  private final HotelService hotelService;

  @Override
  public List<RoomResponseDTO> getAllRooms() {
    return roomMapper.map(roomRepository.findAll());
  }

  @Override
  public RoomResponseDTO getRoomById(Long id) {
    return roomRepository.findById(id)
        .map(roomMapper::map)
        .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
  }

  @Override
  public RoomResponseDTO create(RoomPostRequestDTO room) {
    hotelService.getHotelById(room.getHotelId());
    Room toBeSaved = roomMapper.map(room);
    return roomMapper.map(roomRepository.save(toBeSaved));
  }

  @Override
  public RoomResponseDTO update(Long id, RoomPutRequestDTO updatedRoom) {
    return roomRepository.findById(id)
        .map(room -> {
          room.setRoomNumber(updatedRoom.getRoomNumber());
          room.setCapacity(updatedRoom.getCapacity());
          room.setPricePerNight(updatedRoom.getPricePerNight());
          Room savedRoom = roomRepository.save(room);
          return roomMapper.map(savedRoom);
        })
        .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
  }

  @Override
  public void delete(Long id) {
    roomRepository.findById(id)
        .map(hotel -> {
          roomRepository.deleteById(id);
          return hotel;
        })
        .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
  }

}
