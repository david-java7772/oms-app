package com.ecommerce.oms.config;

import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class Resilience4jConfig {

    @Bean
    public RateLimiterRegistry rateLimiterRegistry() {
        RateLimiterConfig rlConfig = RateLimiterConfig.custom()
                .limitForPeriod(50)               // 50 requests per period
                .limitRefreshPeriod(Duration.ofSeconds(1)) // refresh every second
                .timeoutDuration(Duration.ofMillis(0))
                .build();
        return RateLimiterRegistry.of(rlConfig);
    }
}
