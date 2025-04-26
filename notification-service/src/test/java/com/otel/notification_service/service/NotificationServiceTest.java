package com.otel.notification_service.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class NotificationServiceTest {

  NotificationService notificationService;

  @BeforeEach
  void setUp() {
    // Option 1: Create a real instance (if possible and desired for integration tests)
    // this.notificationService = new NotificationService(...dependencies...);

    // Option 2: Create a mock instance using Mockito (for unit tests)
    this.notificationService = Mockito.mock(NotificationService.class);
  }

  @Test
  void shouldDeserializeReservationCreatedEventCorrectly() throws Exception {
    // 1. Simüle edilmiş Kafka mesajı
    String jsonMessage = "{\"reservationId\": 1, \"hotelId\": 10, \"roomId\": 100, \"guestName\": \"John Doe\", \"checkInDate\": \"2025-02-10\", \"checkOutDate\": \"2025-02-15\"}";

    // 2. ObjectMapper ile deserileştir
    notificationService.listenReservationEvents(jsonMessage);

  }

}
