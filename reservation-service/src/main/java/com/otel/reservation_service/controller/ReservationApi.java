package com.otel.reservation_service.controller;

import com.otel.reservation_service.common_config.response.WrapperCollectionResponse;
import com.otel.reservation_service.common_config.response.WrapperResponse;
import com.otel.reservation_service.request.ReservationRequestDTO;
import com.otel.reservation_service.response.ReservationResponseDTO;
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

@RequestMapping("/reservations")
public interface ReservationApi {

  @GetMapping
  WrapperCollectionResponse<ReservationResponseDTO> getAllReservations();

  @GetMapping("/{id}")
  WrapperResponse<ReservationResponseDTO> getReservationById(@PathVariable Long id);

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  WrapperResponse<ReservationResponseDTO> createReservation(
      @Valid @RequestBody ReservationRequestDTO reservation);

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  WrapperResponse<Void> deleteReservation(@PathVariable Long id);

  @DeleteMapping("/{id}/cancel")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  WrapperResponse<Void> cancelReservation(@PathVariable Long id);

  @PutMapping("/{id}")
  WrapperResponse<ReservationResponseDTO> updateReservation(@PathVariable Long id,
      @Valid @RequestBody ReservationRequestDTO reservation);
}
