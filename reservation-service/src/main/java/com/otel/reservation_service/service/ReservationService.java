package com.otel.reservation_service.service;

import com.otel.reservation_service.request.ReservationRequestDTO;
import com.otel.reservation_service.response.ReservationResponseDTO;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface ReservationService {

  List<ReservationResponseDTO> getAllReservations();

  ReservationResponseDTO getReservationById(Long id);

  @Transactional
  ReservationResponseDTO create(ReservationRequestDTO reservationRequest);

  void delete(Long id);
}
