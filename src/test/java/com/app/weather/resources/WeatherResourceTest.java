package com.app.weather.resources;

import com.app.weather.domain.Weather;
import com.app.weather.service.WeatherService;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class WeatherResourceTest {
    @InjectMocks
    private WeatherResource weatherResource;

    @Mock
    private WeatherService weatherService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void CorrectlyReturnsWeatherForValidCity() throws BadRequestException {
        Weather mockResponse = new Weather();
        when(weatherService.getWeather("Melbourne")).thenReturn(mockResponse);

        Weather response = weatherResource.getGeoLocation("Melbourne");

        assertEquals(mockResponse, response);
    }

    @Test
    public void CorrectlyThrowsForInvalidCity() throws BadRequestException {
        BadRequestException exception = new BadRequestException("No such location");

        when(weatherService.getWeather("Narnia")).thenThrow(exception);

        assertThrows(exception.getClass(), () -> weatherResource.getGeoLocation("Narnia"));
    }
}
