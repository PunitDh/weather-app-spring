package com.app.weather.service;

import com.app.weather.dao.OpenWeatherMapDAO;
import com.app.weather.domain.Geolocation;
import com.app.weather.domain.Weather;
import com.fasterxml.jackson.annotation.JacksonInject;
import org.apache.coyote.BadRequestException;
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

    public Weather getWeather(String location) throws BadRequestException {
        List<Geolocation> geolocations = openWeatherMapDAO.getGeolocation(location);
        if (geolocations.isEmpty()) {
            throw new BadRequestException(String.format("No such location found: '%s'", location));
        }
        BigDecimal latitude = geolocations.get(0).lat;
        BigDecimal longitude = geolocations.get(0).lon;
        return openWeatherMapDAO.getWeather(latitude, longitude);
    }
}
