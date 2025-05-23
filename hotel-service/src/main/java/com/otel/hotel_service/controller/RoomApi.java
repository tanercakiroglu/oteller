package com.otel.hotel_service.controller;

import com.otel.hotel_service.common_config.response.WrapperCollectionResponse;
import com.otel.hotel_service.common_config.response.WrapperResponse;
import com.otel.hotel_service.request.RoomPostRequestDTO;
import com.otel.hotel_service.request.RoomPutRequestDTO;
import com.otel.hotel_service.response.RoomResponseDTO;
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

@RequestMapping("/rooms")
public interface RoomApi {

  @GetMapping
  WrapperCollectionResponse<RoomResponseDTO> getAllRooms();

  @GetMapping("/{id}")
  WrapperResponse<RoomResponseDTO> getRoomById(@PathVariable Long id);

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  WrapperResponse<RoomResponseDTO> createRoom(@RequestBody @Valid RoomPostRequestDTO room);

  @PutMapping("/{id}")
  WrapperResponse<RoomResponseDTO> updateRoom(@PathVariable Long id,
      @RequestBody @Valid RoomPutRequestDTO updatedRoom);

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  WrapperResponse<Void> deleteRoom(@PathVariable Long id);
}
