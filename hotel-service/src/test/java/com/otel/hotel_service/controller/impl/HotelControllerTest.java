package com.otel.hotel_service.controller.impl;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.otel.hotel_service.entity.Hotel;
import com.otel.hotel_service.repository.HotelRepository;
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
class HotelControllerTest {


  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private HotelRepository hotelRepository;

  @Autowired
  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    hotelRepository.deleteAll();
  }

  @Test
  void getAllHotels_ShouldReturnEmptyList_WhenNoHotelsExist() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/hotels"))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty());
  }

  @Test
  void createHotel_ShouldReturnCreatedHotel_WhenHotelIsCreated() throws Exception {
    Hotel hotel = new Hotel();
    hotel.setName("Test Hotel");
    hotel.setAddress("Test Address");
    hotel.setStarRating(5);

    mockMvc.perform(MockMvcRequestBuilders.post("/hotels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(hotel)))
        .andExpect(status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value("Test Hotel"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.address").value("Test Address"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.data.starRating").value(5));
  }

}