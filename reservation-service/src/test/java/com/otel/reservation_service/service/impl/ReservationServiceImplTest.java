package com.otel.reservation_service.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.otel.reservation_service.entity.Reservation;
import com.otel.reservation_service.mapper.ReservationMapperImpl;
import com.otel.reservation_service.repository.ReservationRepository;
import com.otel.reservation_service.request.ReservationRequestDTO;
import com.otel.reservation_service.response.ReservationResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.kafka.core.KafkaTemplate;

class ReservationServiceImplTest {


  @Mock
  private ReservationRepository reservationRepository;

  @Mock
  private KafkaTemplate<String, String> kafkaTemplate;

  @Mock
  private ObjectMapper objectMapper;

  @InjectMocks
  private ReservationServiceImpl reservationService;

  @Spy
  ReservationMapperImpl reservationMapper;

  @Spy
  ReservationValidatorImpl reservationValidator;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void shouldCreateReservationAndSendKafkaMessage() throws Exception {
    LocalDate now = LocalDate.now();
    Reservation reservation = new Reservation();
    reservation.setCheckInDate(now);
    reservation.setHotelId(1L);
    reservation.setRoomId(101L);
    reservation.setGuestName("John Doe");
    reservation.setCheckOutDate(now.plusDays(3));

    when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

    when(objectMapper.writeValueAsString(any())).thenReturn("{\"reservationId\": 1, \"hotelId\": 1, \"roomId\": 101, \"guestName\": \"John Doe\"}");

    ReservationRequestDTO reservationRequest = new ReservationRequestDTO();
    reservationRequest.setCheckInDate(now);
    reservationRequest.setHotelId(1L);
    reservationRequest.setRoomId(101L);
    reservationRequest.setGuestName("John Doe");
    reservationRequest.setCheckOutDate(now.plusDays(3));
    ReservationResponseDTO createdReservation = reservationService.create(
        reservationRequest);

    verify(reservationRepository, times(1)).save(reservation);
    verify(kafkaTemplate, times(1)).send(any(), anyString());
    assertThat(createdReservation.getCheckOutDate()).isEqualTo(reservation.getCheckOutDate());
    assertThat(createdReservation.getCheckInDate()).isEqualTo(reservation.getCheckInDate());
    assertThat(createdReservation.getGuestName()).isEqualTo(reservation.getGuestName());
    assertThat(createdReservation.getHotelId()).isEqualTo(reservation.getHotelId());
    assertThat(createdReservation.getRoomId()).isEqualTo(reservation.getRoomId());
  }

  @Test
  void shouldGetReservationById() {
    // 1. Bir rezervasyon oluştur
    Reservation reservation = new Reservation();
    reservation.setId(1L);
    reservation.setHotelId(1L);
    reservation.setRoomId(101L);
    reservation.setGuestName("John Doe");

    when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservation));

    ReservationResponseDTO foundReservation = reservationService.getReservationById(1L);

    verify(reservationRepository, times(1)).findById(1L);
    assertThat(foundReservation.getCheckOutDate()).isEqualTo(reservation.getCheckOutDate());
    assertThat(foundReservation.getCheckInDate()).isEqualTo(reservation.getCheckInDate());
    assertThat(foundReservation.getGuestName()).isEqualTo(reservation.getGuestName());
    assertThat(foundReservation.getHotelId()).isEqualTo(reservation.getHotelId());
    assertThat(foundReservation.getRoomId()).isEqualTo(reservation.getRoomId());
  }

  @Test
  void shouldGetAllReservations() {

    Reservation reservation1 = new Reservation();
    reservation1.setId(1L);
    Reservation reservation2 = new Reservation();
    reservation2.setId(2L);
    List<Reservation> reservations = Arrays.asList(reservation1, reservation2);

    when(reservationRepository.findAll()).thenReturn(reservations);

    List<ReservationResponseDTO> allReservations = reservationService.getAllReservations();

    verify(reservationRepository, times(1)).findAll();
    assertThat(allReservations).hasSize(2);
  }

  @Test
  void shouldThrowExceptionWhenReservationNotFound() {
    when(reservationRepository.findById(1L)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> reservationService.getReservationById(1L))
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessageContaining("1");
  }

}