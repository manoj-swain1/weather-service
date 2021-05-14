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
import springfox.documentation.spring.web.json.Json;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

        mockMvc.perform(get("/weather/forecast")
                .contentType("application/json")
                .param("city", "Pune")
                .content(objectMapper.writeValueAsString(DummyData.dummyWeather(LocalDateTime.now()))))
                .andExpect(status().isOk());
    }

    @Test
    public void whenInValidInput_thenReturns400() throws Exception {
        when(weatherService.weatherForecast(any(), any()))
                .thenReturn(new ResponseEntity(new Json("invalid input"), HttpStatus.BAD_REQUEST));
        mockMvc.perform(get("/weather/forecast")
                .contentType("application/json")
                .param("city", "Pune")
                .content(objectMapper.writeValueAsString(DummyData.dummyWeather(LocalDateTime.now()))))
                .andExpect(status().is4xxClientError());
    }
}