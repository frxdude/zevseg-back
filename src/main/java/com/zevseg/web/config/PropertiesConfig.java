package com.zevseg.web.config;

import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;

@Component
@PropertySources(
        @PropertySource(value = "classpath:/key.properties", encoding = "UTF-8")
)
public class PropertiesConfig {
}