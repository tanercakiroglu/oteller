package com.otel.hotel_service.mapper;

import com.otel.hotel_service.entity.Hotel;
import com.otel.hotel_service.request.HotelRequestDTO;
import com.otel.hotel_service.response.HotelResponseDTO;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HotelMapper {

  List<HotelResponseDTO> map(List<Hotel> hotels);

  HotelResponseDTO map(Hotel hotel);

  Hotel map(HotelRequestDTO hotel);
}
