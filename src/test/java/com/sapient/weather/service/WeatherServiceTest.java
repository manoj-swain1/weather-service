package com.sapient.weather.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sapient.weather.dto.DummyData;
import com.sapient.weather.dto.WeatherResponseDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.client.MockRestServiceServer;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withBadRequest;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(WeatherService.class)
public class WeatherServiceTest {
    @Autowired
    private MockRestServiceServer server;


    @Autowired
    private WeatherService weatherService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void test_success() throws Exception {
        String responseString =
                objectMapper.writeValueAsString(DummyData.dummyWeather(LocalDateTime.now()));
        this.server.expect(requestTo("https://api.openweathermap.org/data/2.5/forecast?q=Pune&appid=59c6dd543e5f429f56395a0ca3e8b79e&units=metric"))
                .andRespond(withSuccess(responseString, MediaType.APPLICATION_JSON));

        ResponseEntity<?> response = weatherService.weatherForecast("Pune");

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        List<WeatherResponseDTO> data = (List<WeatherResponseDTO>) response.getBody();
        Assertions.assertNotNull(data);

    }

    @Test
    public void test_failure() throws Exception {
        this.server.expect(requestTo("https://api.openweathermap.org/data/2.5/forecast?q=Pune&appid=59c6dd543e5f429f56395a0ca3e8b79e&units=metric"))
                .andRespond(withBadRequest());

        ResponseEntity<?> response = weatherService.weatherForecast("Pune");

        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }
}