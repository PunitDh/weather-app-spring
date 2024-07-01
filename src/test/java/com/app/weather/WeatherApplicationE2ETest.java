package com.app.weather;

import com.app.weather.config.OpenWeatherMapConfig;
import com.app.weather.dao.OpenWeatherMapDAO;
import com.app.weather.dao.http.HttpOpenWeatherMapDAO;
import com.app.weather.domain.Geolocation;
import com.app.weather.domain.Weather;
import com.app.weather.resources.WeatherResource;
import com.app.weather.service.WeatherService;
import jakarta.servlet.ServletContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = { WeatherApplicationE2ETest.TestConfig.class })
@WebAppConfiguration
@Import(WeatherResource.class)
class WeatherApplicationE2ETest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        Weather mockResponse = new Weather();
        mockResponse.lat = BigDecimal.TEN;
        mockResponse.lon = BigDecimal.TEN;
        mockResponse.timezone = "Australia/Melbourne";
        mockResponse.timezone_offset = 36000;
        mockResponse.current = new Weather.WeatherDetails();
        mockResponse.current.temp = BigDecimal.TEN;

        Geolocation geolocation = new Geolocation();
        geolocation.lat = BigDecimal.TEN;
        geolocation.lon = BigDecimal.TEN;

        List<Geolocation> geolocationResponse = List.of(geolocation);

        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), isNull(), any(ParameterizedTypeReference.class))).thenReturn(ResponseEntity.ok(geolocationResponse));
        when(restTemplate.getForObject(anyString(), eq(Weather.class))).thenReturn(mockResponse);
    }

    @Test
    public void TestServletContext() {
        ServletContext servletContext = webApplicationContext.getServletContext();

        assertNotNull(servletContext);
        assertInstanceOf(MockServletContext.class, servletContext);
    }

    @Test
    public void TestGetWeatherData() throws Exception {
        mockMvc.perform(get("/weather").param("location", "Melbourne"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lat").value(10))
                .andExpect(jsonPath("$.lon").value(10))
                .andExpect(jsonPath("$.timezone").value("Australia/Melbourne"))
                .andExpect(jsonPath("$.current.temp").value(10));
    }

    @TestConfiguration
    @ComponentScan(basePackageClasses = { WeatherResource.class })
    static class TestConfig {
        @Bean
        @Primary
        public OpenWeatherMapConfig openWeatherMapConfig() {
            OpenWeatherMapConfig config = new OpenWeatherMapConfig();
            OpenWeatherMapConfig.Endpoints endpoints = new OpenWeatherMapConfig.Endpoints();
            endpoints.setWeather("/data/2.5/weather");
            endpoints.setGeolocation("/geo/1.0/direct");
            config.setBaseUrl("http://base.url");
            config.setApiKey("api-key");
            config.setEndpoints(endpoints);
            return config;
        }

        @Bean
        public OpenWeatherMapDAO openWeatherMapDAO(RestTemplate restTemplate, OpenWeatherMapConfig openWeatherMapConfig) {
            return new HttpOpenWeatherMapDAO(restTemplate, openWeatherMapConfig);
        }

        @Bean
        public WeatherService weatherService(OpenWeatherMapDAO openWeatherMapDAO) {
            return new WeatherService(openWeatherMapDAO);
        }
    }
}
