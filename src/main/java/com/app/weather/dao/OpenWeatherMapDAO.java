package com.app.weather.dao;

import com.app.weather.domain.Geolocation;
import com.app.weather.domain.Weather;

import java.math.BigDecimal;
import java.util.List;

public interface OpenWeatherMapDAO {
    List<Geolocation> getGeolocation(String location);
    Weather getWeather(BigDecimal latitude, BigDecimal longitude);
}