package com.otel.reservation_service.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
public class Reservation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long hotelId;
  private Long roomId;
  private String guestName;
  private LocalDate checkInDate;
  private LocalDate checkOutDate;
  @CreationTimestamp
  private LocalDateTime createdAt;
}