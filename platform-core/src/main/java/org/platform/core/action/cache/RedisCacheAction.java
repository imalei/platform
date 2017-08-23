/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: DesCryptoAction.java
 * @package org.platform.core.action.crypto
 * @author leise
 * @date 2016年5月30日 上午10:07:00
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.action.cache;

import org.platform.core.action.Action;
import org.platform.core.action.enums.CacheType;
import org.platform.core.annotation.action.CacheAction;
import org.platform.core.error.exception.PFException;
import org.platform.core.utils.ActionAnnotationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * @classname: DesCryptoAction
 * @description: TODO(这里用一句话描述这个类的作用)
 */
@Component
public class RedisCacheAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(RedisCacheAction.class);

    @Value(value = "${platform.cache.enable:false}")
    private boolean enableCache;

    @Autowired
    private RedisTemplate<String, ?> redisTemplate;

    public Object execute(String actionId, String declareClassName, Object object) throws PFException {

        logger.info("[执行action开始]--[actionId:{}--[调用方:{}]------", actionId, declareClassName);

        if (!enableCache) {
            logger.info("[执行action结束]--[actionId:{}--[调用方:{}]----缓存未启用--", actionId, declareClassName);
            return null;
        }

        Assert.notNull(object, "the cache data must be not null");
        logger.info("[actionId:{}]--[调用方:{}]--[缓存数据:{}]", actionId, declareClassName, object);

        CacheAction cacheAction = ActionAnnotationUtils.getAnnotation(declareClassName, actionId, CacheAction.class);
        Assert.notNull(cacheAction, "the cacheAction config must be not null");

        String actionName = cacheAction.name();
        logger.info("[actionId:{}]--[调用方:{}]--[actionName:{}]", actionId, declareClassName, actionName);

        byte[] key = null;
        byte[] value = transform(object);
        
        CacheType cacheType = cacheAction.cacheType();

        String resultData = null;

        switch (cacheType) {
            case PUT:
                long timeout = cacheAction.timeout();
                set(key, value, timeout);
                break;

            case GET:
                resultData = null;
                break;

            case DEL:
                resultData = null;
                break;

            default:
                return null;
        }

        logger.info("[执行action结束]--[actionId:{}]--[调用方:{}]------", actionId, declareClassName);

        return resultData;

    }

    public void set(final byte[] key, final byte[] value, final long timeout) {

        redisTemplate.execute(new RedisCallback<Long>() {

            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {

                connection.set(key, value);

                if (timeout > 0) {
                    connection.expire(key, timeout);
                }

                return 1L;
            }

        });
    }

    public byte[] get(final byte[] key) {

        byte[] result = redisTemplate.execute(new RedisCallback<byte[]>() {

            @Override
            public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.get(key);
            }

        });

        return result;

    }

    public void del(final byte[]... keys) {

        redisTemplate.execute(new RedisCallback<Long>() {

            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                Long count = 0L;

                for (int i = 0; i < keys.length; i++) {
                    connection.del(keys[i]);
                    count++;
                }
                return count;

            }

        });
    }

    public byte[] transform(Object obj) {

        byte[] value = null;

        if (obj instanceof String) {
            String data = (String) obj;
            value = data.getBytes();
        }

        return value;
    }

}
