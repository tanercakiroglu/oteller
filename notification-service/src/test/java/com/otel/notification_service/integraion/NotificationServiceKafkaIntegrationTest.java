package com.otel.notification_service.integraion;

import com.otel.notification_service.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;

@SpringBootTest
@EmbeddedKafka
public class NotificationServiceKafkaIntegrationTest {

  @Autowired
  private NotificationService notificationService;

  @Value("${spring.kafka.topic.reservation}")
  private String topic;

  @Autowired
  private KafkaTemplate<String, String> kafkaTemplate;

  @Test
  void shouldConsumeReservationCreatedEvent() throws InterruptedException {
    String testMessage = "{\"reservationId\": 1, \"hotelId\": 10, \"roomId\": 100, \"guestName\": \"John Doe\", \"checkInDate\": \"2025-02-10\", \"checkOutDate\": \"2025-02-15\"}";
    kafkaTemplate.send(topic, testMessage);
    Thread.sleep(1000);
    // log.info("Reservation Created Event Received: {}", reservationEvent); loglarda bunu gorebilirsiniz
  }
}

