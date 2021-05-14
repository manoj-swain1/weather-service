package com.sapient.weather.dto;

import com.sapient.weather.enums.WeatherCondition;
import org.jeasy.random.EasyRandom;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DummyData {

    public static List<WeatherTimeDTO> dummyWeatherTimeDTOList(LocalDateTime date) {
        List<WeatherTimeDTO> data = new ArrayList<>();

        BigDecimal temp = new BigDecimal(25);
        IntStream.rangeClosed(1, 5).forEach(i -> {
            List<WeatherConditionDTO> weatherCondition = new EasyRandom()
                    .objects(WeatherConditionDTO.class, 1).collect(Collectors.toList());
            if (i > 2) {
                weatherCondition.forEach(x -> x.setMain(WeatherCondition.RAIN.toString()));
            } else {
                weatherCondition.forEach(x -> x.setMain(WeatherCondition.CLEAR.toString()));
            }
            WeatherTimeMainDTO weatherTempData = new EasyRandom().nextObject(WeatherTimeMainDTO.class);
            weatherTempData.setTemp_max(temp.add(new BigDecimal(i)));
            weatherTempData.setTemp_min(temp.subtract(new BigDecimal(i)));

            WeatherTimeDTO dto = new WeatherTimeDTO.WeatherTimeDTOBuilder()
                    .dt(date)
                    .main(weatherTempData)
                    .weather(weatherCondition)
                    .build();
            data.add(dto);
        });
        return data;
    }

    public static WeatherDTO dummyWeather(LocalDateTime date) {
        WeatherDTO result = new WeatherDTO();

        result.setList(new ArrayList<>());
        IntStream.rangeClosed(0, 6).forEach(i -> {
            List<WeatherTimeDTO> weatherDaysData = dummyWeatherTimeDTOList(i > 0 ? date.plusDays(i) : date);
            result.getList().addAll(weatherDaysData);
        });
        return result;
    }
}
