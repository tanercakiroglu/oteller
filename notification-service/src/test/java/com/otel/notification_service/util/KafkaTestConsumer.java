package com.otel.notification_service.util;

// Özel Test Consumer
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import java.util.concurrent.CountDownLatch;

@Component
public class KafkaTestConsumer {

  private CountDownLatch latch = new CountDownLatch(1);
  private String payload;

  @KafkaListener(topics = "reservation-created-topic") // Doğru topic adını belirtin
  public void receive(ConsumerRecord<?, String> consumerRecord) {
    payload = consumerRecord.value();
    latch.countDown();
  }

  public CountDownLatch getLatch() {
    return latch;
  }

  public String getPayload() {
    return payload;
  }

  public void send(String message) {
    // Test mesajını Kafka'ya göndermek için gerekli kod (örneğin, KafkaTemplate kullanılarak)
    // Bu örnekte, bu kısım basitleştirilmiştir.
  }
}