/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: CacheManager.java
 * @package org.platform.core.cache
 * @author leise
 * @date 2016年7月20日 下午5:22:17
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.cache;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @classname: CacheManager
 * @description: TODO(这里用一句话描述这个类的作用)
 */
@Configuration
@EnableCaching
public class RedisCacheConfig extends CachingConfigurerSupport {

    @Bean(name = "redisCacheManager")
    public CacheManager cacheManager(RedisTemplate<String, ?> redisTemplate) {

        CacheManager cacheManager = new RedisCacheManager(redisTemplate);
        return cacheManager;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Bean
    public RedisTemplate<String, ?> redisTemplate(RedisConnectionFactory redisConnectionFactory) {

        RedisTemplate<String, ?> redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

}
