package com.otel.hotel_service.mapper;

import com.otel.hotel_service.entity.Room;
import com.otel.hotel_service.request.RoomPostRequestDTO;
import com.otel.hotel_service.response.RoomResponseDTO;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoomMapper {

  List<RoomResponseDTO> map(List<Room> rooms);

  RoomResponseDTO map(Room room);

  Room map(RoomPostRequestDTO room);
}
