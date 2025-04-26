package com.otel.reservation_service.util;

// Özel Test Consumer

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import lombok.Getter;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Getter
@Component
public class KafkaTestConsumer {

  private final List<String> payloads = new ArrayList<>();
  private final CountDownLatch latch = new CountDownLatch(1);

  @KafkaListener(topics = "${spring.kafka.topic.reservation}", groupId = "${spring.kafka.consumer.group-id}")
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

}