package com.hanssem.remodeling.content.common.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTypeResolverBuilder;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@Slf4j
@Configuration
@EnableCaching
@EnableRedisRepositories
@RequiredArgsConstructor
public class RedisConfig extends CachingConfigurerSupport {

    private final ObjectMapper objectMapper;

    @Value("${spring.redis.custom.name-prefix.base}")
    private String baseCacheNamePrefix;

    @Value("${spring.redis.custom.name-prefix.display}")
    private String displayCacheNamePrefix;

    @Value("${spring.redis.custom.name-prefix.goods}")
    private String goodsCacheNamePrefix;

    @Value("${spring.redis.custom.name-prefix.category}")
    private String categoryCacheNamePrefix;

    @Bean
    @Primary
    public CacheManager baseCacheManager(RedisConnectionFactory connectionFactory) {

        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(this.getGenericJackson2JsonRedisSerializer()))
                .disableCachingNullValues()
                .prefixCacheNameWith(baseCacheNamePrefix)
                .entryTtl(Duration.ofDays(1));

        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(connectionFactory)
                .cacheDefaults(redisCacheConfiguration)
                .build();
    }

    @Bean
    public CacheManager displayCacheManager(RedisConnectionFactory connectionFactory) {

        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(this.getGenericJackson2JsonRedisSerializer()))
                .disableCachingNullValues()
                .prefixCacheNameWith(displayCacheNamePrefix)
                .entryTtl(Duration.ofHours(24));

        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(connectionFactory)
                .cacheDefaults(redisCacheConfiguration)
                .build();
    }

    @Bean
    public CacheManager goodsCacheManager(RedisConnectionFactory connectionFactory) {

        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(this.getGenericJackson2JsonRedisSerializer()))
                .disableCachingNullValues()
                .prefixCacheNameWith(goodsCacheNamePrefix)
                .entryTtl(Duration.ofDays(1));

        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(connectionFactory)
                .cacheDefaults(redisCacheConfiguration)
                .build();
    }

    @Bean
    public CacheManager categoryCacheManager(RedisConnectionFactory connectionFactory) {

        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(this.getGenericJackson2JsonRedisSerializer()))
                .disableCachingNullValues()
                .prefixCacheNameWith(categoryCacheNamePrefix)
                .entryTtl(Duration.ofDays(1));

        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(connectionFactory)
                .cacheDefaults(redisCacheConfiguration)
                .build();
    }

    @Bean
    public GenericJackson2JsonRedisSerializer getGenericJackson2JsonRedisSerializer() {
        // GenericJackson2JsonRedisSerializer() 그대로 사용할 경우, objectMapper가 신규 생성된다.
        // 프로젝트에 적용된 objectMapper와 동일한 옵션으로 사용하기 위하여, objectMapper를 복사해서 GenericJackson2JsonRedisSerializer를 생성한다.

        ObjectMapper redisObjectMapper = objectMapper.copy();

        redisObjectMapper.disable(SerializationFeature.INDENT_OUTPUT);

        StdTypeResolverBuilder typer = new DefaultTypeResolverBuilder(DefaultTyping.EVERYTHING, redisObjectMapper.getPolymorphicTypeValidator());
        typer = typer.init(JsonTypeInfo.Id.CLASS, null);
        typer = typer.inclusion(JsonTypeInfo.As.PROPERTY);

        redisObjectMapper.setDefaultTyping(typer);

        log.debug("# create GenericJackson2JsonRedisSerializer from objectMapper.");
        return new GenericJackson2JsonRedisSerializer(redisObjectMapper);
    }

    @Override
    public CacheErrorHandler errorHandler() {
        return new CacheErrorHandler() {
            @Override
            public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
                log.error("Unable to GET from cache [{}] : {}", cache.getName(), exception.getMessage());
            }

            @Override
            public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
                log.error("Unable to PUT from cache [{}] : {}", cache.getName(), exception.getMessage());
            }

            @Override
            public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
                log.error("Unable to EVICT from cache [{}] : {}", cache.getName(), exception.getMessage());
                throw exception;
            }

            @Override
            public void handleCacheClearError(RuntimeException exception, Cache cache) {
                log.error("Unable to CLEAR from cache [{}] : {}", cache.getName(), exception.getMessage());
                throw exception;
            }
        };
    }

}
