package com.otel.hotel_service.controller.impl;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.otel.hotel_service.entity.Hotel;
import com.otel.hotel_service.entity.Room;
import com.otel.hotel_service.repository.HotelRepository;
import com.otel.hotel_service.repository.RoomRepository;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class RoomControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private RoomRepository roomRepository;

  @Autowired
  private HotelRepository hotelRepository;

  @Autowired
  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    roomRepository.deleteAll();
  }

  @Test
  void getAllHotels_ShouldReturnEmptyList_WhenNoRoomExist() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/rooms"))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());
  }

  @Test
  void createRoom_ShouldReturnCreatedRoom_WhenRoomIsCreated() throws Exception {
    Hotel hotel = new Hotel();
    hotel.setName("Test Hotel");
    hotel.setAddress("Test Address");
    hotel.setStarRating(5);

    Hotel savedHotel = hotelRepository.save(hotel);

    Room room = new Room();
    room.setCapacity(3);
    room.setPricePerNight(new BigDecimal("12.50"));
    room.setHotelId(savedHotel.getId());
    room.setRoomNumber("32");


    mockMvc.perform(MockMvcRequestBuilders.post("/rooms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(room)))
        .andExpect(status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.roomNumber").value("32"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.createdAt").exists())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.updatedAt").exists());
  }

}