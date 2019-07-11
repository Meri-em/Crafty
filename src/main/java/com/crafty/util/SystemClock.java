package com.crafty.util;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import org.springframework.stereotype.Component;

@Component
public class SystemClock {
    
    public LocalDate date() {
        return LocalDate.now(Clock.systemUTC());
    }

    public LocalDate date(ZoneId zoneId) {
        return LocalDate.now(zoneId);
    }

    public LocalDateTime dateTime() {
        return LocalDateTime.now(Clock.systemUTC());
    }

    public Instant instant() {
        return this.dateTime().toInstant(ZoneOffset.UTC);
    }
}
