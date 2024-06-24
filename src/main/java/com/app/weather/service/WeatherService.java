package com.app.weather.service;

import com.app.weather.dao.OpenWeatherMapDAO;
import com.app.weather.domain.Geolocation;
import com.fasterxml.jackson.annotation.JacksonInject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherService {
    @JacksonInject
    private final OpenWeatherMapDAO openWeatherMapDAO;

    public WeatherService(OpenWeatherMapDAO openWeatherMapDAO) {
        this.openWeatherMapDAO = openWeatherMapDAO;
    }

    public List<Geolocation> getGeolocation(String city) {
        return openWeatherMapDAO.getGeolocation(city);
    }
}
