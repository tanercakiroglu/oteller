package com.otel.reservation_service.repository;

import com.otel.reservation_service.entity.Reservation;
import jakarta.persistence.LockModeType;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("SELECT r FROM Reservation r WHERE r.roomId = :roomId AND r.hotelId=:hotelId AND (r.checkInDate < :checkOutDate AND r.checkOutDate > :checkInDate) OR (r.checkInDate <= :checkOutDate AND r.checkOutDate >= :checkInDate)")
  List<Reservation> findConflictingReservations(
      @Param("roomId") Long roomId,
      @Param("hotelId") Long hotelId,
      @Param("checkInDate") LocalDate checkInDate,
      @Param("checkOutDate") LocalDate checkOutDate);
}