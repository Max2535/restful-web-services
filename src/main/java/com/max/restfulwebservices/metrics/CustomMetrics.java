package com.max.restfulwebservices.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class CustomMetrics {

    public CustomMetrics(MeterRegistry registry) {
        registry.counter("custom.metric.counter", "type", "example").increment();
    }
}