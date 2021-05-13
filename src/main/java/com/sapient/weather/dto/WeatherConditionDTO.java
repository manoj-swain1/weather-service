package com.sapient.weather.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

public class WeatherConditionDTO {

    @Getter
    @Setter
    private Integer id;

    @Getter
    @Setter
    private String main;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private String icon;

}
