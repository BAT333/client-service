package com.example.service.client.config.element;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class TaskScheduling {
    @Scheduled(fixedDelay = 12, timeUnit = TimeUnit.HOURS)
    @CacheEvict(value = "Client", allEntries =true)
    public void clearingClientCache() {
        log.info("Cleared 'Client' cache on: " + LocalDateTime.now());
    }
}
