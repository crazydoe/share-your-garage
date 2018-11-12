package com.michal.garageshare.logging.dto;

import ch.qos.logback.classic.Logger;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * View Model object for storing a Logback logger.
 */
@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class LoggerDTO {

    private String name;

    private String level;

    public LoggerDTO(Logger logger) {
        this.name = logger.getName();
        this.level = logger.getEffectiveLevel().toString();
    }
}
