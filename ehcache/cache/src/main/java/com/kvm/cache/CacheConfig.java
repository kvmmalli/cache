package com.kvm.cache;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import com.kvm.cache.entity.Employee;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.*;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.io.Serializable;
import java.time.Duration;

import static org.ehcache.config.builders.CacheConfigurationBuilder.newCacheConfigurationBuilder;


@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager getCacheManager() {
        CachingProvider cachingProvider = Caching.getCachingProvider();
        CacheManager cacheManager = cachingProvider.getCacheManager();

                        CacheConfiguration<Integer, Object> CacheConfigurationBuilder=  newCacheConfigurationBuilder(
                                Integer.class, Object.class,
                                ResourcePoolsBuilder.newResourcePoolsBuilder()
                                        .disk(100, MemoryUnit.MB, true)
                                        .heap(10000, EntryUnit.ENTRIES))
                                      //  .disk(10000, MemoryUnit.MB, false))
                        .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(600)))
                        .withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(Duration.ofSeconds(300)))
                        .withEvictionAdvisor((key, value) -> false)
                        .build();

        javax.cache.configuration.Configuration<Integer, Object> configuration =  Eh107Configuration.fromEhcacheCacheConfiguration(CacheConfigurationBuilder);


        cacheManager.createCache("a1cache", configuration);
        return cacheManager;

    }

}
