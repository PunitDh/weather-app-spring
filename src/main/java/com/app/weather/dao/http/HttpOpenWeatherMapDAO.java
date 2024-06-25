package com.app.weather.dao.http;

import com.app.weather.config.OpenWeatherMapConfig;
import com.app.weather.dao.OpenWeatherMapDAO;
import com.app.weather.domain.Geolocation;
import com.app.weather.domain.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class HttpOpenWeatherMapDAO implements OpenWeatherMapDAO {
    private final RestTemplate restTemplate;
    private final OpenWeatherMapConfig openWeatherMapConfig;

    @Autowired
    public HttpOpenWeatherMapDAO(RestTemplate restTemplate, OpenWeatherMapConfig openWeatherMapConfig) {
        this.restTemplate = restTemplate;
        this.openWeatherMapConfig = openWeatherMapConfig;
    }

    @Override
    @Retryable(backoff = @Backoff(delay = 2000))
    public List<Geolocation> getGeolocation(String location) {
        String url = getUriBuilder()
                .path(openWeatherMapConfig.getEndpoints().getGeolocation())
                .queryParam("q", location)
                .build()
                .toUriString();

        return doGet(url, new ParameterizedTypeReference<>() {});
    }

    @Override
    @Retryable(backoff = @Backoff(delay = 2000))
    public Weather getWeather(BigDecimal latitude, BigDecimal longitude) {
        String url = getUriBuilder()
                .path(openWeatherMapConfig.getEndpoints().getWeather())
                .queryParam("lat", latitude)
                .queryParam("lon", longitude)
                .build()
                .toUriString();

        return restTemplate.getForObject(url, Weather.class);
    }

    private <T> T doGet(String url, ParameterizedTypeReference<T> responseType) {
        return restTemplate.exchange(url,  HttpMethod.GET, null, responseType).getBody();
    }

    private UriComponentsBuilder getUriBuilder() {
        return UriComponentsBuilder
                .fromHttpUrl(openWeatherMapConfig.getBaseUrl())
                .queryParam("appid", openWeatherMapConfig.getApiKey());
    }
}
