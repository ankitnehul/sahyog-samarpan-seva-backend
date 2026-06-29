package com.sahyogsamarpan.seva.web;

import java.time.Instant;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Lightweight liveness endpoint used by Railway health checks and for quick
 * manual verification that the API is up. Kept separate from Actuator so it
 * stays stable regardless of management endpoint configuration.
 */
@RestController
@RequestMapping("/api")
public class HealthController {

    @GetMapping("/health")
    public Map<String, Object> health() {
        return Map.of(
                "status", "UP",
                "service", "seva-backend",
                "timestamp", Instant.now().toString());
    }
}
