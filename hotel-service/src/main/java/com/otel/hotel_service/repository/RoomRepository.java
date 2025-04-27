package com.otel.hotel_service.repository;


import com.otel.hotel_service.entity.Room;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

  void deleteByHotelId(Long id);

  Optional<Room> findByRoomNumberAndHotelId(String roomNumber, Long hotelId);
}
