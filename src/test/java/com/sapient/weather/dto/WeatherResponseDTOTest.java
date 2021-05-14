package com.sapient.weather.dto;

import com.sapient.weather.enums.WeatherCondition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class WeatherResponseDTOTest {
    private WeatherResponseDTO weatherResponseDTO;
    private List<WeatherTimeDTO> data = new ArrayList<>();
    private LocalDateTime date = LocalDateTime.now();

    @BeforeEach
    void setup() {
        data = DummyData.dummyWeatherTimeDTOList(date);
        weatherResponseDTO = new WeatherResponseDTO();
    }

    @Test
    void updateMinMaxTemperatureOfTheDay() {
        for (WeatherTimeDTO dto : data) {
            weatherResponseDTO.updateWeatherData(dto);
        }

        Assertions.assertNotNull(weatherResponseDTO);
        Assertions.assertEquals(new BigDecimal(30), weatherResponseDTO.getMax_temp());
        Assertions.assertEquals(new BigDecimal(20), weatherResponseDTO.getMin_temp());
        Assertions.assertEquals(date.toLocalDate(), weatherResponseDTO.getDate());
        Assertions.assertEquals("", weatherResponseDTO.getWeather());
        Assertions.assertEquals("", weatherResponseDTO.getWarning());
    }

    @Test
    void whenRainyWeather() {
        for (WeatherTimeDTO dto : data) {
            weatherResponseDTO.updateWeatherData(dto);
        }

        weatherResponseDTO.setWarning();

        Assertions.assertNotNull(weatherResponseDTO);
        Assertions.assertEquals(new BigDecimal(30), weatherResponseDTO.getMax_temp());
        Assertions.assertEquals(new BigDecimal(20), weatherResponseDTO.getMin_temp());
        Assertions.assertEquals(date.toLocalDate(), weatherResponseDTO.getDate());
        Assertions.assertEquals("RAIN", weatherResponseDTO.getWeather());
        Assertions.assertEquals("Carry Umbrella", weatherResponseDTO.getWarning());
    }

    @Test
    void whenTemperatureIsGreaterThan40() {
        data.get(0).getMain().setTemp_max(new BigDecimal(42.1));
        for (WeatherTimeDTO dto : data) {

            dto.getWeather().get(0).setMain(WeatherCondition.CLEAR.name());
            weatherResponseDTO.updateWeatherData(dto);
        }

        weatherResponseDTO.setWarning();

        Assertions.assertNotNull(weatherResponseDTO);
        Assertions.assertEquals(new BigDecimal(42.1), weatherResponseDTO.getMax_temp());
        Assertions.assertEquals(new BigDecimal(20), weatherResponseDTO.getMin_temp());
        Assertions.assertEquals(date.toLocalDate(), weatherResponseDTO.getDate());
        Assertions.assertEquals("CLEAR", weatherResponseDTO.getWeather());
        Assertions.assertEquals("Use sunscreen lotion", weatherResponseDTO.getWarning());
    }


}