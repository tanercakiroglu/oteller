package com.otel.notification_service.service;

import static org.junit.jupiter.api.Assertions.*;

import com.otel.notification_service.util.KafkaTestConsumer;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
@DirtiesContext
class NotificationServiceTest {

  @Autowired
  private NotificationService notificationService; // NotificationService'inizi enjekte edin

  @Autowired
  private KafkaTestConsumer testConsumer; // Özel bir test consumer'ı (aşağıda tanımlanmış)

  @Test
  public void testConsumeReservationCreatedEvent() throws Exception {
    // 1. Test mesajını Kafka'ya gönder
    String testMessage = "{\"reservationId\": 1, \"hotelId\": 10, \"roomId\": 100, \"guestName\": \"John Doe\", \"checkInDate\": \"2025-02-10\", \"checkOutDate\": \"2025-02-15\"}";
    testConsumer.send(testMessage);

    // 2. Mesajın tüketildiğini doğrula
    boolean messageConsumed = testConsumer.getLatch().await(10, TimeUnit.SECONDS);
    assertTrue(messageConsumed);
    assertEquals(testConsumer.getPayload(),testMessage);

    // 3. (Opsiyonel) NotificationService'in mesajı doğru işlediğini doğrula (örneğin, loglama yapıldı mı?)
  }
}
