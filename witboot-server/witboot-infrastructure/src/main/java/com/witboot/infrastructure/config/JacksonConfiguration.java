package com.witboot.infrastructure.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.witboot.common.Constants;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Jackson 配置类，用于自定义 Jackson ObjectMapper 的行为。
 * 主要功能包括：
 * 1. 禁用时间戳格式序列化和时区调整反序列化。
 * 2. 为 Java 8 时间类型（LocalDateTime、LocalDate、LocalTime）配置自定义的序列化和反序列化格式。
 * 3. 安装所需的 Jackson 模块。
 */
@Configuration
public class JacksonConfiguration {

    /**
     * 自定义 Jackson ObjectMapper 的构建器。
     * 通过 Jackson2ObjectMapperBuilderCustomizer 接口实现对 ObjectMapper 的定制化配置。
     *
     * @return 返回一个 Jackson2ObjectMapperBuilderCustomizer 实例
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> {
            builder
                    // 安装所需的 Jackson 模块
                    .modulesToInstall(new JavaTimeModule())
                    // 禁用将日期序列化为时间戳的功能
                    .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                    // 禁用反序列化时自动调整日期到上下文时区的功能
                    .featuresToDisable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
                    // 为 LocalDateTime 类型配置自定义序列化器
                    .serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(Constants.DateTime.DEFAULT_DATE_TIME_FORMAT)))
                    // 为 LocalDate 类型配置自定义序列化器
                    .serializerByType(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(Constants.DateTime.DEFAULT_DATE_FORMAT)))
                    // 为 LocalTime 类型配置自定义序列化器
                    .serializerByType(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(Constants.DateTime.DEFAULT_TIME_FORMAT)))
                    // 为 LocalDateTime 类型配置自定义反序列化器
                    .deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(Constants.DateTime.DEFAULT_DATE_TIME_FORMAT)))
                    // 为 LocalDate 类型配置自定义反序列化器
                    .deserializerByType(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(Constants.DateTime.DEFAULT_DATE_FORMAT)))
                    // 为 LocalTime 类型配置自定义反序列化器
                    .deserializerByType(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(Constants.DateTime.DEFAULT_TIME_FORMAT)));
        };
    }
}

