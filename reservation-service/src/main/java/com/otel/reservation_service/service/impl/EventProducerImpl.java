package com.otel.reservation_service.service.impl;

import com.otel.reservation_service.response.ReservationResponseDTO;
import com.otel.reservation_service.service.EventProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventProducerImpl implements EventProducer {

  private final KafkaTemplate<String, ReservationResponseDTO> kafkaTemplate;

  @Value("${kafka.topic.reservation-created}")
  private String reservationCreatedTopic;

  @Value("${kafka.topic.reservation-cancelled}")
  private String reservationCancelledTopic;

  @Value("${kafka.topic.reservation-updated}")
  private String reservationUpdatedTopic;

  @Override
  public void sendReservationCreatedEvent(ReservationResponseDTO reservation) {
    try {
      log.info("reservation created ,sending information to kafka: {}", reservation);
      kafkaTemplate.send(reservationCreatedTopic, reservation);
    } catch (Exception e) {
      log.error("Reservation created event could be sent due to:", e);
    }
  }

  @Override
  public void sendReservationCancelledEvent(ReservationResponseDTO reservation) {
    try {
      log.info("reservation cancelled ,sending information to kafka: {}", reservation);
      kafkaTemplate.send(reservationCancelledTopic, reservation);
    } catch (Exception e) {
      log.error("Reservation cancelled event could be sent due to:", e);
    }
  }

  @Override
  public void sendReservationUpdatedEvent(ReservationResponseDTO reservation) {
    try {
      log.info("reservation updated ,sending information to kafka: {}", reservation);
      kafkaTemplate.send(reservationUpdatedTopic, reservation);
    } catch (Exception e) {
      log.error("Reservation updated event could be sent due to:", e);
    }
  }

}
