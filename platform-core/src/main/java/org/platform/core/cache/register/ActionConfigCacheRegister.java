/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: ActionDefinedCache.java
 * @package org.platform.core.cache.local
 * @author leise
 * @date 2016年7月20日 下午3:15:59
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.cache.register;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.platform.core.action.ActionConfigurable;
import org.platform.core.cache.CacheRegister;
import org.platform.core.error.exception.ExceptionBuilder;
import org.platform.core.initializer.register.ActionConfigRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Lists;

/**
 * @classname: ActionDefinedCache
 * @description: TODO(这里用一句话描述这个类的作用)
 */

@Component
public class ActionConfigCacheRegister implements CacheRegister {

    @Autowired(required = false)
    private List<ActionConfigurable> actionConfigurables = Lists.newArrayList();

    public static LoadingCache<String, ActionConfigRegister> actionConfigCache = CacheBuilder.newBuilder().build(
            new CacheLoader<String, ActionConfigRegister>() {

                @Override
                public ActionConfigRegister load(final String actionConfigClassName) throws Exception {
                    return new ActionConfigRegister(actionConfigClassName);
                }
            });

    public static ActionConfigRegister get(final String actionConfigClassName) {
        try {
            return actionConfigCache.get(actionConfigClassName);
        }
        catch (ExecutionException e) {
            throw ExceptionBuilder.newException().withCause(e).build();
        }
    }

    public static Map<String, ActionConfigRegister> getAll() {
        return actionConfigCache.asMap();
    }

    public void loadAll() {
        for (ActionConfigurable actionConfig : actionConfigurables) {
            get(actionConfig.getClass().getName());
        }
    }

    @Override
    public String toString() {
        return getAll().toString();
    }

}
