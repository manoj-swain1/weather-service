package com.sapient.weather.service;

import com.sapient.weather.dto.WeatherDTO;
import com.sapient.weather.dto.WeatherMapDTO;
import com.sapient.weather.dto.WeatherMapTimeDTO;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.spring.web.json.Json;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WeatherService {
    @Value("${weather.api}")
    private String URI;
    @Value("${weather.api.apiKey}")
    private String API_ID;
    @Value("${weather.unit}")
    private String defaultUnit;

    private final RestTemplate restTemplate;

    public WeatherService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    //    @Cached(expire = 10, timeUnit = TimeUnit.MINUTES)
    public ResponseEntity<?> weatherForecastAverage(String city, String metric) {
        List<WeatherDTO> result = new ArrayList<>();
        try {
            WeatherMapDTO weatherMap = this.restTemplate.getForObject(this.url(city, metric), WeatherMapDTO.class);

            for (LocalDate reference = LocalDate.now();
                 reference.isBefore(LocalDate.now().plusDays(3));
                 reference = reference.plusDays(1)) {
                final LocalDate ref = reference;
                List<WeatherMapTimeDTO> collect = weatherMap.getList().stream()
                        .filter(x -> x.getDt().toLocalDate().equals(ref)).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(collect)) {
                    result.add(this.average(collect));
                }

            }
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(new Json(e.getResponseBodyAsString()), e.getStatusCode());
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    private WeatherDTO average(List<WeatherMapTimeDTO> list) {
        WeatherDTO result = new WeatherDTO();

        for (WeatherMapTimeDTO item : list) {
            result.setDate(item.getDt().toLocalDate());
            result.updateWeatherData(item);
        }

        result.setWarning();

        return result;
    }

    private String url(String city, String unit) {
        String tempUnit = Optional.ofNullable(unit).filter(Strings::isNotEmpty).orElse(defaultUnit);
        return String.format(URI.concat("?q=%s").concat("&appid=%s").concat("&units=%s"), city, API_ID, tempUnit);
    }
}
