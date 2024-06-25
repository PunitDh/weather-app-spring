package com.app.weather.service;

import com.app.weather.dao.OpenWeatherMapDAO;
import com.app.weather.domain.Geolocation;
import com.app.weather.domain.Weather;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class WeatherServiceTest {
    @InjectMocks
    private WeatherService weatherService;

    @Mock
    private OpenWeatherMapDAO openWeatherMapDAO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void CorrectlyReturnsWeatherData() throws BadRequestException {
        String location = "Melbourne";
        Geolocation geoLocation = new Geolocation();

        List<Geolocation> geolocations = List.of(geoLocation);
        Weather weather = new Weather();

        when(openWeatherMapDAO.getGeolocation(location)).thenReturn(geolocations);
        when(openWeatherMapDAO.getWeather(geoLocation.lat, geoLocation.lon)).thenReturn(weather);

        Weather weatherResponse = weatherService.getWeather(location);

        assertNotNull(weatherResponse);
        assertEquals(weatherResponse, weather);
    }

    @Test
    public void CorrectlyThrowsAnErrorWhenLocationDoesNotExist() {
        String location = "Narnia";
        Geolocation geoLocation = new Geolocation();

        List<Geolocation> geolocations = Collections.emptyList();
        Weather weather = new Weather();

        when(openWeatherMapDAO.getGeolocation(location)).thenReturn(geolocations);
        when(openWeatherMapDAO.getWeather(geoLocation.lat, geoLocation.lon)).thenReturn(weather);

        assertThrows(BadRequestException.class, () -> weatherService.getWeather(location));
    }
}
