package com.app.weather.resources;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.actuate.health.HealthIndicator;

import static org.junit.jupiter.api.Assertions.*;

public class HealthResourceTest {
    @InjectMocks
    private HealthResource healthResource;

    @Mock
    private HealthIndicator healthIndicator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        healthResource = new HealthResource(healthIndicator);
    }

    @Test
    public void CorrectlyReturnsHealthCheck() {
        assertDoesNotThrow(() -> healthResource.getHealthCheck());
    }
}
