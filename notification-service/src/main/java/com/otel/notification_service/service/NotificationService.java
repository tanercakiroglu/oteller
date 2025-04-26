package com.otel.notification_service.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.otel.notification_service.model.ReservationResponseDTO;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {

  private final ObjectMapper objectMapper;

  @KafkaListener(topics = "${kafka.topic.reservation-created}", groupId = "${spring.kafka.consumer.group-id}")
  public void listenReservationEvents(String message) {
    try {
      ReservationResponseDTO reservationEvent = objectMapper.readValue(message, ReservationResponseDTO.class);
      // Rezervasyon bilgilerini logla
      log.info("Reservation Created Event Received: {}", reservationEvent);

      // E-posta gönderme simülasyonu (isteğe bağlı)
      simulateEmailNotification(reservationEvent);

    } catch (IOException e) {
      log.error("Error processing reservation event: {}", e.getMessage());
    }
  }

  private void simulateEmailNotification(ReservationResponseDTO reservationEvent) {
    // Bu metot, e-posta gönderme işlemini simüle eder.
    // Gerçek bir uygulamada, bir e-posta kütüphanesi veya servisi kullanılabilir.
    String guestName = reservationEvent.getGuestName();
    String checkInDate = reservationEvent.getCheckInDate().toString();
    String checkOutDate = reservationEvent.getCheckOutDate().toString();

    String emailMessage = String.format(
        "Dear %s,\nYour reservation from %s to %s has been confirmed.",
        guestName, checkInDate, checkOutDate
    );

   log.info("Simulating Email Notification: {}" , emailMessage);
  }
}
