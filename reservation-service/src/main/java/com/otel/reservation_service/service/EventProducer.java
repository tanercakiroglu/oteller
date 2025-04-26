package com.otel.reservation_service.service;

import com.otel.reservation_service.response.ReservationResponseDTO;

public interface EventProducer {

  void sendReservationCreatedEvent(ReservationResponseDTO reservation);

  void sendReservationCancelledEvent(ReservationResponseDTO reservation);

  void sendReservationUpdatedEvent(ReservationResponseDTO reservation);
}
