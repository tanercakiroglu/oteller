package com.otel.reservation_service.mapper;

import com.otel.reservation_service.entity.Reservation;
import com.otel.reservation_service.request.ReservationRequestDTO;
import com.otel.reservation_service.response.ReservationResponseDTO;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

  List<ReservationResponseDTO> map(List<Reservation> reservations);

  ReservationResponseDTO map(Reservation reservation);

  Reservation map(ReservationRequestDTO reservationRequest);
}
