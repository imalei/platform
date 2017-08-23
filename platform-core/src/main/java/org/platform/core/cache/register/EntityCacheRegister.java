/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: EntityCacle.java
 * @package org.platform.core.cache
 * @author leise
 * @date 2016年7月20日 下午2:28:53
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.cache.register;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.platform.core.base.entity.Entity;
import org.platform.core.cache.CacheRegister;
import org.platform.core.error.exception.ExceptionBuilder;
import org.platform.core.initializer.register.EntityRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;

/**
 * @classname: EntityCacle
 * @description: TODO(这里用一句话描述这个类的作用)
 */
@Component
public class EntityCacheRegister implements CacheRegister {

    @Autowired(required = false)
    private List<Entity> entitys = Lists.newArrayList();

    public static LoadingCache<String, EntityRegister> entityLoadingCache = CacheBuilder.newBuilder().build(
            new CacheLoader<String, EntityRegister>() {

                @Override
                public EntityRegister load(final String entityClassName) throws Exception {
                    return new EntityRegister(entityClassName);
                }
            });

    public static EntityRegister get(final String entityClassName) {
        try {
            return entityLoadingCache.get(entityClassName);
        }
        catch (ExecutionException e) {
            throw ExceptionBuilder.newException().withCause(e).build();
        }
    }

    public static Map<String, EntityRegister> getAll() {
        return entityLoadingCache.asMap();
    }

    public void loadAll() {
        for (Entity entity : entitys) {
            get(entity.getClass().getName());
        }
    }

    @Override
    public String toString() {
        return getAll().toString();
    }

}
