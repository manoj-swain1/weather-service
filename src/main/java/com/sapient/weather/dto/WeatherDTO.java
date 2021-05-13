package com.sapient.weather.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sapient.weather.enums.WeatherCondition;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.BinaryOperator;

public class WeatherDTO implements Serializable {

    private static final long serialVersionUID = 5763148931413501367L;
    private static final BigDecimal HOT_TEMP_LIMIT = new BigDecimal(40.0);

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Getter
    @Setter
    private LocalDate date;

    @Getter
    @Setter
    private BigDecimal max_temp;

    @Getter
    @Setter
    private BigDecimal min_temp;

    @Getter
    @Setter
    private String weather;

    @Getter
    @Setter
    private String warning;

    @JsonIgnore
    private List<String> weatherConditions;


    public WeatherDTO() {
        this.max_temp = BigDecimal.ZERO;
        this.min_temp = new BigDecimal(Integer.MAX_VALUE);
        this.warning = "";
        this.weatherConditions = new ArrayList<>();
    }

    public void updateWeatherData(WeatherMapTimeDTO map) {
        if (this.max_temp.compareTo(map.getMain().getTemp_max()) < 0) {
            this.max_temp = map.getMain().getTemp_max();
        }
        if (this.min_temp.compareTo(map.getMain().getTemp_min()) > 0) {
            this.min_temp = map.getMain().getTemp_min();
        }
        this.weatherConditions.add(map.getWeather().stream()
                .findFirst().map(WeatherConditionDTO::getMain).orElse(""));

    }

    public void setWarning() {
        if (this.max_temp.compareTo(HOT_TEMP_LIMIT) > 0) {
            this.warning = "Use sunscreen lotion";
        }
        this.weather = this.weatherConditions.stream()
                .reduce(BinaryOperator.maxBy(Comparator.comparingInt(o -> Collections.frequency(weatherConditions, o)))).orElse("");
        if (WeatherCondition.RAIN.toString().equalsIgnoreCase(this.weather)) {
            this.warning = "Carry Umbrella";
        }
    }

}
