package com.sapient.weather.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class WeatherDTO implements Serializable {

    private static final long serialVersionUID = 1253320017739887653L;

    @Getter
    @Setter
    private String cod;

    @Getter
    @Setter
    private BigDecimal message;

    @Getter
    @Setter
    private Integer cnt;

    @JacksonXmlProperty(localName = "list")
    @JacksonXmlElementWrapper(localName = "list", useWrapping = true)
    @Getter
    @Setter
    private List<WeatherTimeDTO> list;

}
