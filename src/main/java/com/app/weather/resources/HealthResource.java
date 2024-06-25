package com.app.weather.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/healthcheck")
public class HealthResource {
    private final HealthIndicator healthIndicator;

    @Autowired
    public HealthResource(@Qualifier("pingHealthContributor") HealthIndicator healthIndicator) {
        this.healthIndicator = healthIndicator;
    }

    @GetMapping
    public Health getHealthCheck() {
        return healthIndicator.health();
    }
}
