package com.otel.hotel_service.controller.impl;

import static org.springframework.http.HttpStatus.CREATED;

import com.otel.hotel_service.common_config.Loggable;
import com.otel.hotel_service.common_config.response.WrapperCollectionResponse;
import com.otel.hotel_service.common_config.response.WrapperResponse;
import com.otel.hotel_service.controller.RoomApi;
import com.otel.hotel_service.request.RoomPostRequestDTO;
import com.otel.hotel_service.request.RoomPutRequestDTO;
import com.otel.hotel_service.response.RoomResponseDTO;
import com.otel.hotel_service.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Loggable
public class RoomController implements RoomApi {

  private final RoomService roomService;

  @Override
  public WrapperCollectionResponse<RoomResponseDTO> getAllRooms() {
    return WrapperCollectionResponse.of(roomService.getAllRooms());
  }

  @Override
  public WrapperResponse<RoomResponseDTO> getRoomById(Long id) {
    return WrapperResponse.of(roomService.getRoomById(id));
  }

  @Override
  public WrapperResponse<RoomResponseDTO> createRoom(RoomPostRequestDTO room) {
    WrapperResponse<RoomResponseDTO> created = WrapperResponse.of(roomService.create(room));
    created.setStatus(CREATED.value());
    return created;
  }

  @Override
  public WrapperResponse<RoomResponseDTO> updateRoom(Long id, RoomPutRequestDTO updatedRoom) {
    return WrapperResponse.of(roomService.update(id,updatedRoom));
  }

  @Override
  public WrapperResponse<Void> deleteRoom(Long id) {
    roomService.delete(id);
    return WrapperResponse.empty();
  }
}
