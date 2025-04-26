package com.otel.reservation_service.controller.impl;

import static org.springframework.http.HttpStatus.CREATED;

import com.otel.reservation_service.common_config.response.WrapperCollectionResponse;
import com.otel.reservation_service.common_config.response.WrapperResponse;
import com.otel.reservation_service.controller.ReservationApi;
import com.otel.reservation_service.request.ReservationRequestDTO;
import com.otel.reservation_service.response.ReservationResponseDTO;
import com.otel.reservation_service.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReservationController implements ReservationApi {

  private final ReservationService reservationService;

  @Override
  public WrapperCollectionResponse<ReservationResponseDTO> getAllReservations() {
    return WrapperCollectionResponse.of(reservationService.getAllReservations());
  }

  @Override
  public WrapperResponse<ReservationResponseDTO> getReservationById(Long id) {
    return WrapperResponse.of(reservationService.getReservationById(id));
  }

  @Override
  public WrapperResponse<ReservationResponseDTO> createReservation(
      ReservationRequestDTO reservation) {
    WrapperResponse<ReservationResponseDTO> created = WrapperResponse.of(
        reservationService.create(reservation));
    created.setStatus(CREATED.value());
    return created;
  }

  @Override
  public WrapperResponse<Void> deleteReservation(Long id) {
    reservationService.delete(id);
    return WrapperResponse.empty();
  }

  @Override
  public WrapperResponse<Void> cancelReservation(Long id) {
    reservationService.cancel(id);
    return WrapperResponse.empty();
  }

  @Override
  public WrapperResponse<ReservationResponseDTO> updateReservation(Long id,
      ReservationRequestDTO reservation) {
    return WrapperResponse.of(reservationService.update(id,reservation));
  }
}
