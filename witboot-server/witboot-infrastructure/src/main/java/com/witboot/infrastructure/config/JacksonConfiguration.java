package com.witboot.infrastructure.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.witboot.infrastructure.common.Constants;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class JacksonConfiguration {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
            builder
                    .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                    .featuresToDisable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
                    .serializerByType(LocalDateTime.class,
                            new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(Constants.DateTime.DEFAULT_DATE_TIME_FORMAT)))
                    .serializerByType(LocalDate.class,
                            new LocalDateSerializer(DateTimeFormatter.ofPattern(Constants.DateTime.DEFAULT_DATE_FORMAT)))
                    .serializerByType(LocalTime.class,
                            new LocalTimeSerializer(DateTimeFormatter.ofPattern(Constants.DateTime.DEFAULT_TIME_FORMAT)))
                    .deserializerByType(LocalDateTime.class,
                            new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(Constants.DateTime.DEFAULT_DATE_TIME_FORMAT)))
                    .deserializerByType(LocalDate.class,
                            new LocalDateDeserializer(DateTimeFormatter.ofPattern(Constants.DateTime.DEFAULT_DATE_FORMAT)))
                    .deserializerByType(LocalTime.class,
                            new LocalTimeDeserializer(DateTimeFormatter.ofPattern(Constants.DateTime.DEFAULT_TIME_FORMAT)));
        };
    }
}
