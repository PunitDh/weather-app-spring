package com.app.weather.dao;

import com.app.weather.domain.Geolocation;
import com.app.weather.domain.Weather;

import java.util.List;

public interface OpenWeatherMapDAO {
    public List<Geolocation> getGeolocation(String city);
    public Weather getWeather(double latitude, double longitude);
}