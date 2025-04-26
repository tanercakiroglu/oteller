package com.otel.reservation_service.service.impl;


import static com.otel.reservation_service.common_config.constant.ErrorCodes.RESERVATION_SERVICE_RESERVATION_REQUEST_ALREADY_RESERVED;

import com.otel.reservation_service.common_config.Loggable;
import com.otel.reservation_service.common_config.exception.BusinessException;
import com.otel.reservation_service.entity.Reservation;
import com.otel.reservation_service.mapper.ReservationMapper;
import com.otel.reservation_service.repository.ReservationRepository;
import com.otel.reservation_service.request.ReservationRequestDTO;
import com.otel.reservation_service.response.ReservationResponseDTO;
import com.otel.reservation_service.service.EventProducer;
import com.otel.reservation_service.service.ReservationService;
import com.otel.reservation_service.service.ReservationValidator;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Loggable
@Slf4j
public class ReservationServiceImpl implements ReservationService {

  private final ReservationRepository reservationRepository;
  private final ReservationMapper reservationMapper;
  private final EventProducer eventProducer;
  private final ReservationValidator reservationValidator;

  @Override
  public List<ReservationResponseDTO> getAllReservations() {
    return reservationMapper.map(reservationRepository.findAll());
  }

  @Override
  public ReservationResponseDTO getReservationById(Long id) {
    return reservationRepository.findById(id)
        .map(reservationMapper::map)
        .orElseThrow(() -> new EntityNotFoundException((String.valueOf(id))));
  }

  @Override
  @Transactional
  public ReservationResponseDTO create(ReservationRequestDTO reservationRequest) {
    reservationValidator.validateDates(reservationRequest.getCheckInDate(),
        reservationRequest.getCheckOutDate());
    List<Reservation> conflictingReservations = reservationRepository.findConflictingReservations(
        reservationRequest.getHotelId(),
        reservationRequest.getRoomId(),
        reservationRequest.getCheckInDate(),
        reservationRequest.getCheckOutDate());

    if (!conflictingReservations.isEmpty()) {
      throw new BusinessException(RESERVATION_SERVICE_RESERVATION_REQUEST_ALREADY_RESERVED);
    }
    Reservation savedReservation = reservationRepository.save(reservationMapper.map(reservationRequest));
    ReservationResponseDTO response = reservationMapper.map(savedReservation);
    eventProducer.sendReservationCreatedEvent(response);
    return response;
  }

  @Override
  public void delete(Long id) {
    reservationRepository.findById(id)
        .map(reservation -> {
          reservationRepository.deleteById(id);
          return reservation;
        })
        .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
  }

  @Override
  @Transactional
  public void cancel(Long id) {
    Reservation reservation = reservationRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));

    reservationRepository.delete(reservation);
    eventProducer.sendReservationCancelledEvent(reservationMapper.map(reservation));
  }

  @Override
  @Transactional
  public ReservationResponseDTO update(Long id, ReservationRequestDTO updateRequest) {
    reservationValidator.validateDates(updateRequest.getCheckInDate(),
        updateRequest.getCheckOutDate());
    Reservation existingReservation = reservationRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));

    // Aynı oda için güncellenmiş tarihlerle çakışan rezervasyon kontrolü (mevcut rezervasyon hariç)
    List<Reservation> conflictingReservations = reservationRepository.findConflictingReservations(
            updateRequest.getRoomId(),updateRequest.getHotelId() ,updateRequest.getCheckOutDate(), updateRequest.getCheckInDate()
        ).stream()
        .filter(r -> !r.getId().equals(id)) // Mevcut rezervasyonu filtrele
        .toList();

    if (!conflictingReservations.isEmpty()) {
      throw new BusinessException(RESERVATION_SERVICE_RESERVATION_REQUEST_ALREADY_RESERVED);
    }

    existingReservation.setHotelId(updateRequest.getHotelId());
    existingReservation.setRoomId(updateRequest.getRoomId());
    existingReservation.setGuestName(updateRequest.getGuestName());
    existingReservation.setCheckInDate(updateRequest.getCheckInDate());
    existingReservation.setCheckOutDate(updateRequest.getCheckOutDate());

    ReservationResponseDTO updatedReservation = reservationMapper.map(reservationRepository.save(existingReservation));

    eventProducer.sendReservationUpdatedEvent(updatedReservation);

    return updatedReservation;
  }
}
