package com.otel.reservation_service.util;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import lombok.Getter;
import lombok.Setter;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class KafkaTestConsumer {

  private final List<String> payloads = new ArrayList<>();
  private CountDownLatch latch = new CountDownLatch(1);

  @KafkaListener(topics = "${kafka.topic.reservation-created}", groupId = "${spring.kafka.consumer.group-id}")
  public void receive(ConsumerRecord<?, String> consumerRecord) {
    payloads.add(consumerRecord.value());
    latch.countDown();
  }

  public String getLatestPayload() {
    return payloads.get(payloads.size() - 1);
  }

  public void reset() {
    payloads.clear();
    latch.countDown();
  }

  @KafkaListener(topics = "reservation-cancelled", groupId = "test-group")
  public void consumeCancelled(ConsumerRecord<?, String> consumerRecord) {
    payloads.add(consumerRecord.value());
    latch.countDown();
  }

  @KafkaListener(topics = "reservation-updated", groupId = "test-group")
  public void consumeUpdated(ConsumerRecord<?, String> consumerRecord) {
    payloads.add(consumerRecord.value());
    latch.countDown();
  }

}