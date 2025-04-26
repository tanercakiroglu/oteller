package com.otel.reservation_service.controller.impl;

import static org.assertj.core.api.Assertions.assertThat;

import com.otel.reservation_service.common_config.response.WrapperCollectionResponse;
import com.otel.reservation_service.common_config.response.WrapperResponse;
import com.otel.reservation_service.entity.Reservation;
import com.otel.reservation_service.repository.ReservationRepository;
import com.otel.reservation_service.request.ReservationRequestDTO;
import com.otel.reservation_service.response.ReservationResponseDTO;
import com.otel.reservation_service.util.KafkaTestConsumer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.test.context.EmbeddedKafka;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EmbeddedKafka
class ReservationControllerTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private ReservationRepository reservationRepository;

  @Autowired
  private KafkaTestConsumer testConsumer;

  @BeforeEach
  void setUp() {
    testConsumer.reset();
    reservationRepository.deleteAll();
  }

  @Test
  void shouldCreateReservationAndSendKafkaMessage() throws Exception {
    LocalDate now = LocalDate.now();
    ReservationRequestDTO reservation = getReservationRequest(now);

    ResponseEntity<WrapperResponse<ReservationResponseDTO>> response = postReservation(reservation);

    Reservation savedReservation = reservationRepository.findById(response.getBody().get().getId())
        .orElse(null);
    assertThat(savedReservation).isNotNull();
    assertThat(savedReservation.getHotelId()).isEqualTo(1L);
    assertThat(savedReservation.getRoomId()).isEqualTo(101L);
    assertThat(savedReservation.getGuestName()).isEqualTo("John Doe");
    assertThat(savedReservation.getCheckInDate()).isEqualTo(now);
    assertThat(savedReservation.getCheckOutDate()).isEqualTo(now.plusDays(3));

    boolean messageConsumed = testConsumer.getLatch().await(1, TimeUnit.SECONDS);
    assertThat(messageConsumed).isTrue();
    assertThat(testConsumer.getPayloads().get(0)).contains("\"hotelId\":1");
  }


  @Test
  void shouldGetAllReservations() {
    LocalDate now = LocalDate.now();
    ReservationRequestDTO reservation1 = getReservationRequest(now);
    reservation1.setGuestName("John Doe");
    postReservation(reservation1);
    ReservationRequestDTO reservation2 = getReservationRequest(now.plusDays(4));
    reservation2.setGuestName("Jane Smith");
    postReservation(reservation2);

    ResponseEntity<WrapperCollectionResponse<ReservationResponseDTO>> response = restTemplate.exchange(
        "/reservations",
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<>() {
        }
    );

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    ArrayList<ReservationResponseDTO> actual = (ArrayList<ReservationResponseDTO>) response.getBody()
        .get();
    assertThat(actual).hasSize(2);
    assertThat(actual.get(0).getGuestName()).isEqualTo("John Doe");
    assertThat(actual.get(1).getGuestName()).isEqualTo("Jane Smith");
  }

  @Test
  void shouldGetReservationById() {

    LocalDate now = LocalDate.now();
    ReservationRequestDTO reservation1 = getReservationRequest(now);
    ResponseEntity<WrapperResponse<ReservationResponseDTO>> savedReservationResponse = postReservation(
        reservation1);
    ReservationResponseDTO savedReservation = savedReservationResponse.getBody().get();

    ResponseEntity<WrapperResponse<ReservationResponseDTO>> response = restTemplate.exchange(
        "/reservations/" + savedReservation.getId(),
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<>() {
        }
    );

    ReservationResponseDTO data = response.getBody().getData();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(data.getGuestName()).isEqualTo("John Doe");
    assertThat(data.getId()).isEqualTo(savedReservation.getId());
    assertThat(data.getHotelId()).isEqualTo(1L);
    assertThat(data.getRoomId()).isEqualTo(101L);
    assertThat(data.getGuestName()).isEqualTo("John Doe");
    assertThat(data.getCheckInDate()).isEqualTo(now);
    assertThat(data.getCheckOutDate()).isEqualTo(now.plusDays(3));
  }

  @Test
  void shouldReturnNotFoundForInvalidId() {
    ResponseEntity<WrapperResponse<ReservationResponseDTO>> response = restTemplate.exchange(
        "/reservations/" +99,
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<>() {
        }
    );

    assertThat(response.getBody().getErrorMessage().get(0).contains("99")).isEqualTo(true);
  }

  private ResponseEntity<WrapperResponse<ReservationResponseDTO>> postReservation(
      ReservationRequestDTO reservation) {
    ResponseEntity<WrapperResponse<ReservationResponseDTO>> response = restTemplate.exchange(
        "/reservations",
        HttpMethod.POST,
        new HttpEntity<>(reservation),
        new ParameterizedTypeReference<>() {
        });
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    return response;
  }


  private static ReservationRequestDTO getReservationRequest(LocalDate now) {
    ReservationRequestDTO reservation = new ReservationRequestDTO();
    reservation.setHotelId(1L);
    reservation.setRoomId(101L);
    reservation.setGuestName("John Doe");
    reservation.setCheckInDate(now);
    reservation.setCheckOutDate(now.plusDays(3));
    return reservation;
  }
}