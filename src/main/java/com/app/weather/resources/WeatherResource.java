package com.app.weather.resources;

import com.app.weather.domain.Weather;
import com.app.weather.service.WeatherService;
import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weather")
public class WeatherResource {

    private final WeatherService weatherService;

    public WeatherResource(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping
    public Weather getGeoLocation(@RequestParam String location) throws BadRequestException {
        return weatherService.getWeather(location);
    }
}
