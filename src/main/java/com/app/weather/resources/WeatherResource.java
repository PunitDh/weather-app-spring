package com.app.weather.resources;

import com.app.weather.domain.Geolocation;
import com.app.weather.service.WeatherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class WeatherResource {

    private final WeatherService weatherService;

    public WeatherResource(WeatherService weatherService) {
        this.weatherService = weatherService;
    }


    @GetMapping("/hello")
    public String hello() {
        return "Hello world";
    }

    @GetMapping("/geolocation/{city}")
    public List<Geolocation> getGeoLocation(@PathVariable String city) {
        return weatherService.getGeolocation(city);
    }
}
