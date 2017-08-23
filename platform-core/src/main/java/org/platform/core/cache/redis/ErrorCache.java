/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: ErrorCache.java
 * @package org.platform.core.cache.redis
 * @author leise
 * @date 2016年8月2日 下午1:26:06
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.cache.redis;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.platform.core.action.ActionConfigurable;
import org.platform.core.action.enums.JdbcOpType;
import org.platform.core.action.jdbc.JdbcEntityQueryAction;
import org.platform.core.annotation.action.JdbcEntityAction;
import org.platform.core.cache.annotation.Cacheable;
import org.platform.core.system.entity.TSysError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @classname: ErrorCache
 * @description: TODO(这里用一句话描述这个类的作用)
 */

@Component
public class ErrorCache {

    /** 服务调用方类名 */
    private String declareClassName = ActionConfig.class.getName();

    @Autowired
    private JdbcEntityQueryAction jdbcEntityQueryAction;

    @SuppressWarnings("unchecked")
    @Cacheable(cacheName = "error", cacheManager = "redisCacheManager", key = "#errorNo")
    public TSysError get(String errorNo) {

        System.out.println("从数据库中查询错误信息列表");

        TSysError condition = new TSysError();
        condition.setErrorNo(errorNo);
        List<TSysError> errors = (List<TSysError>) jdbcEntityQueryAction.execute(ActionConfig.ERROR_QUERY,
                declareClassName, condition);

        TSysError error = null;
        if (CollectionUtils.isNotEmpty(errors)) {
            error = errors.get(0);
        }

        return error;
    }

    @Component
    private static class ActionConfig implements ActionConfigurable {

        @JdbcEntityAction(entityClass = TSysError.class, name = "查询错误信息列表", opType = JdbcOpType.QUERY)
        public static final String ERROR_QUERY = "ERROR_QUERY";
    }

}
