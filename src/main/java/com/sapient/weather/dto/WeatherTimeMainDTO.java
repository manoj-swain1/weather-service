package com.sapient.weather.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

public class WeatherTimeMainDTO {

    @Getter
    @Setter
    private BigDecimal temp;

    @Getter
    @Setter
    private BigDecimal temp_min;

    @Getter
    @Setter
    private BigDecimal temp_max;

    @Getter
    @Setter
    private BigDecimal pressure;

    @Getter
    @Setter
    private BigDecimal sea_level;

    @Getter
    @Setter
    private BigDecimal grnd_level;

    @Getter
    @Setter
    private BigDecimal humidity;

    @Getter
    @Setter
    private BigDecimal temp_kf;


}
