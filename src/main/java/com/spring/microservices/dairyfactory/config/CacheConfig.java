package com.spring.microservices.dairyfactory.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/*
@EnableCaching can be applied on DairyFactoryApplication.java as well instead of creating a separate class.
 */

@Configuration
@EnableCaching
public class CacheConfig {
}
