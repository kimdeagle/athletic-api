package com.athletic.api.config;

import com.athletic.api.util.constant.Const;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

@Configuration
public class DateTimeFormatConfig {
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return jacksonObjectMapperBuilder -> {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(Const.DEFAULT_LOCAL_DATE_FORMAT);
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(Const.DEFAULT_LOCAL_DATE_TIME_FORMAT);

            jacksonObjectMapperBuilder.deserializers(new LocalDateDeserializer(dateFormatter));
            jacksonObjectMapperBuilder.deserializers(new LocalDateTimeDeserializer(dateTimeFormatter));

            jacksonObjectMapperBuilder.serializers(new LocalDateSerializer(dateFormatter));
            jacksonObjectMapperBuilder.serializers(new LocalDateTimeSerializer(dateTimeFormatter));
        };
    }
}
