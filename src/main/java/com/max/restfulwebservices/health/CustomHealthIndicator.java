package com.max.restfulwebservices.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class CustomHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        // ตัวอย่าง: ตรวจสอบระบบภายใน
        boolean isServiceRunning = true;

        if (isServiceRunning) {
            return Health.up().withDetail("Service Status", "Running").build();
        } else {
            return Health.down().withDetail("Service Status", "Stopped").build();
        }
    }
}