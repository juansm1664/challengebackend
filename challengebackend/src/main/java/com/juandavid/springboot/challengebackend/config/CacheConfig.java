package com.juandavid.springboot.challengebackend.config;


import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
@EnableAsync
public class CacheConfig {

    public static final String PERCENTAGE_CACHE = "percentageCache";

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(PERCENTAGE_CACHE);

        //Configura Caffeine para el cache PERCENTAGE_CACHE
        cacheManager.setCaffeine(Caffeine.newBuilder()
         //establece Time-to-live: expira 30 minutos después de la última escritura
        .expireAfterWrite(30, TimeUnit.MINUTES)
        .maximumSize(1) // establece el tamaño máximo del caché
        .recordStats()
        );
        return cacheManager;
    }

}
