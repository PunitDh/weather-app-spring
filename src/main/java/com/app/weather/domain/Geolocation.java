package com.app.weather.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;

@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class Geolocation {
    public String name;
    public BigDecimal lat;
    public BigDecimal lon;
    public String country;
    public String state;
}
