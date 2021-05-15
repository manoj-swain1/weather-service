package com.sapient.weather.service;

import com.sapient.weather.dto.WeatherDTO;
import com.sapient.weather.dto.WeatherResponseDTO;
import com.sapient.weather.dto.WeatherTimeDTO;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public WeatherService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public ResponseEntity<?> weatherForecast(String city) {
        List<WeatherResponseDTO> result = new ArrayList<>();
        try {
            WeatherDTO weatherMap = this.restTemplate.getForObject(this.prepareUrl(city), WeatherDTO.class);

            for (LocalDate reference = LocalDate.now();
                 reference.isBefore(LocalDate.now().plusDays(3));
                 reference = reference.plusDays(1)) {
                final LocalDate ref = reference;
                List<WeatherTimeDTO> collect = weatherMap.getList().stream()
                        .filter(x -> x.getDt().toLocalDate().equals(ref)).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(collect)) {
                    result.add(this.createResponse(collect));
                }

            }
        } catch (HttpClientErrorException e) {
            return new ResponseEntity<>(new Json(e.getResponseBodyAsString()), e.getStatusCode());
        }

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    private WeatherResponseDTO createResponse(List<WeatherTimeDTO> list) {
        WeatherResponseDTO result = new WeatherResponseDTO();

        for (WeatherTimeDTO item : list) {
            result.updateWeatherData(item);
        }

        result.setWarning();

        return result;
    }

    private String prepareUrl(String city) {
        return String.format(URI.concat("?q=%s").concat("&appid=%s").concat("&units=%s"), city, API_ID, defaultUnit);
    }
}
