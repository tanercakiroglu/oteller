package com.otel.reservation_service.service.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.otel.reservation_service.common_config.Loggable;
import com.otel.reservation_service.common_config.exception.BusinessException;
import com.otel.reservation_service.entity.Reservation;
import com.otel.reservation_service.mapper.ReservationMapper;
import com.otel.reservation_service.repository.ReservationRepository;
import com.otel.reservation_service.request.ReservationRequestDTO;
import com.otel.reservation_service.response.ReservationResponseDTO;
import com.otel.reservation_service.service.ReservationService;
import com.otel.reservation_service.service.ReservationValidator;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Loggable
@Slf4j
public class ReservationServiceImpl implements ReservationService {

  private final ReservationRepository reservationRepository;
  private final ReservationMapper reservationMapper;
  private final KafkaTemplate<String, String> kafkaTemplate;
  private final ObjectMapper objectMapper;
  private final ReservationValidator reservationValidator;

  @Value("${spring.kafka.topic.reservation}")
  private String reservationTopic;

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
    reservationValidator.validateDates(reservationRequest.getCheckInDate(),reservationRequest.getCheckOutDate());
    List<Reservation> conflictingReservations = reservationRepository.findConflictingReservations(
        reservationRequest.getHotelId(),
        reservationRequest.getRoomId(),
        reservationRequest.getCheckInDate(),
        reservationRequest.getCheckOutDate()
    );

    if (!conflictingReservations.isEmpty()) {
      throw new BusinessException(); // 409 Conflict
    }
    Reservation savedReservation = reservationRepository.save(
        reservationMapper.map(reservationRequest));
    try {
      String reservationJson = objectMapper.writeValueAsString(savedReservation);
      kafkaTemplate.send(reservationTopic, reservationJson);
    } catch (Exception e) {
     log.error("Reservation created event could be sent due to:",e);
    }
    return reservationMapper.map(savedReservation);
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
}
