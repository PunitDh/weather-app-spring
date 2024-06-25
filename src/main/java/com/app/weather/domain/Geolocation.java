package com.app.weather.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;

@JsonSerialize
@JsonIgnoreProperties(ignoreUnknown = true)
public class Geolocation {
    public BigDecimal lat;
    public BigDecimal lon;
}
