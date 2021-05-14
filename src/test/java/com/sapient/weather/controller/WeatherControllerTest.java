package com.sapient.weather.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sapient.weather.dto.DummyData;
import com.sapient.weather.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = WeatherController.class)
public class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherService weatherService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void whenValidInput_thenReturns200() throws Exception {
        String data = objectMapper.writeValueAsString(DummyData.dummyWeather(LocalDateTime.now()));
        when(weatherService.weatherForecast(any(), any()))
                .thenReturn(new ResponseEntity(data, HttpStatus.OK));
        mockMvc.perform(get("/weather/forecast")
                .contentType("application/json")
                .param("city", "Pune"))
                .andExpect(status().isOk())
                .andExpect(content().string(data));
    }

    @Test
    public void whenInValidInput_thenReturns400() throws Exception {
        String response = "invalid input";
        when(weatherService.weatherForecast(any(), any()))
                .thenReturn(new ResponseEntity(response, HttpStatus.BAD_REQUEST));
        mockMvc.perform(get("/weather/forecast")
                .contentType("application/json")
                .param("city", "Pune"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string("invalid input"));
    }
}