package com.app.weather.dao.http;

import com.app.weather.config.OpenWeatherMapConfig;
import com.app.weather.domain.Geolocation;
import com.app.weather.domain.Weather;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class HttpOpenWeatherMapDAOTest {
    @InjectMocks
    private HttpOpenWeatherMapDAO httpOpenWeatherMapDAO;

    @Mock
    private OpenWeatherMapConfig openWeatherMapConfig;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(openWeatherMapConfig.getBaseUrl()).thenReturn("http://base.url");
        when(openWeatherMapConfig.getApiKey()).thenReturn("api-key");
        when(openWeatherMapConfig.getEndpoints()).thenReturn(new OpenWeatherMapConfig.Endpoints() {{
            setWeather("/weather");
            setGeolocation("/geolocation");
        }});
    }

    @Test
    public void CorrectlyCallsGeolocationEndpoint() {
        ArgumentCaptor<String> urlCaptor = ArgumentCaptor.forClass(String.class);
        String location = "Melbourne";

        List<Geolocation> mockResponse = List.of(new Geolocation());

        when(restTemplate.exchange(urlCaptor.capture(), eq(HttpMethod.GET), isNull(), any(ParameterizedTypeReference.class))).thenReturn(ResponseEntity.ok(mockResponse));

        List<Geolocation> response = httpOpenWeatherMapDAO.getGeolocation(location);

        assertEquals("http://base.url/geolocation?appid=api-key&q=Melbourne", urlCaptor.getValue());
        assertEquals(response, mockResponse);
        assertEquals(1, response.size());
    }

    @Test
    public void CorrectlyCallsWeatherEndpoint() {
        ArgumentCaptor<String> urlCaptor = ArgumentCaptor.forClass(String.class);
        BigDecimal latitude = BigDecimal.TEN;
        BigDecimal longitude = BigDecimal.TEN;

        Weather mockResponse = new Weather();

        when(restTemplate.getForObject(urlCaptor.capture(), eq(Weather.class))).thenReturn(mockResponse);

        Weather response = httpOpenWeatherMapDAO.getWeather(latitude, longitude);

        assertEquals("http://base.url/weather?appid=api-key&lat=10&lon=10", urlCaptor.getValue());
        assertEquals(response, mockResponse);
    }
}
