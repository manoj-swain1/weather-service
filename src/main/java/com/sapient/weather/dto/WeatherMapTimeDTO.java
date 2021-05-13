package com.sapient.weather.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

public class WeatherMapTimeDTO {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("dt_txt")
    @Getter
    @Setter
    private LocalDateTime dt;

    @Getter
    @Setter
    private WeatherMapTimeMainDTO main;

    @JacksonXmlProperty(localName = "weather")
    @JacksonXmlElementWrapper(localName = "weather", useWrapping = true)
    @Getter
    @Setter
    private List<WeatherConditionDTO> weather;

}