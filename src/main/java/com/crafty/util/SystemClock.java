package com.crafty.util;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import org.springframework.stereotype.Component;

/**
 * SystemClock class is a wrapper around {@link java.time.LocalDate} and {@link java.time.LocalDateTime}.
 * The purpose of the class is to change globally the date/date time values the application is using internally.
 * By providing a custom day offset you can put the application in "future state".
 * in order to verify/test different functionality that occurs at some point in the future.
 * By default the SystemClock have a day offset of zero which means that it behaves exactly as
 * calling {@link java.time.LocalDate} and {@link java.time.LocalDateTime}.
 *
 * All existing and future calls to {@link java.time.LocalDate} and {@link java.time.LocalDateTime} should go through this class.
 */
@Component
public class SystemClock {
    
//    private final int daysOffset;
//    
//    public SystemClock(int daysOffset) {
//        this.daysOffset = daysOffset;
//    }
    
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
