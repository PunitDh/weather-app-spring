package com.app.weather.service;

import com.app.weather.dao.OpenWeatherMapDAO;
import com.app.weather.domain.Geolocation;
import com.app.weather.domain.Weather;
import com.fasterxml.jackson.annotation.JacksonInject;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class WeatherService {
    @JacksonInject
    private final OpenWeatherMapDAO openWeatherMapDAO;

    public WeatherService(OpenWeatherMapDAO openWeatherMapDAO) {
        this.openWeatherMapDAO = openWeatherMapDAO;
    }

    public Weather getWeather(String city) {
        List<Geolocation> geolocations = openWeatherMapDAO.getGeolocation(city);
        BigDecimal latitude = geolocations.get(0).lat;
        BigDecimal longitude = geolocations.get(0).lon;
        return openWeatherMapDAO.getWeather(latitude, longitude);
    }
}
