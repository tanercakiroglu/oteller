package com.otel.hotel_service.service;

import com.otel.hotel_service.request.RoomPostRequestDTO;
import com.otel.hotel_service.request.RoomPutRequestDTO;
import com.otel.hotel_service.response.RoomResponseDTO;
import java.util.List;

public interface RoomService {

  List<RoomResponseDTO> getAllRooms();

  RoomResponseDTO getRoomById(Long id);

  RoomResponseDTO create(RoomPostRequestDTO room);

  RoomResponseDTO update(Long id, RoomPutRequestDTO updatedRoom);

  void delete(Long id);
}
