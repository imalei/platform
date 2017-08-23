/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: LocalCacheInitializer.java
 * @package org.platform.core.initializer.bean
 * @author leise
 * @date 2016年7月20日 下午3:30:33
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.initializer.bean;

import java.io.Serializable;
import java.util.List;

import org.platform.core.cache.CacheRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

/**
 * @classname: LocalCacheInitializer
 * @description: TODO(这里用一句话描述这个类的作用)
 */
@Component
public class CacheRegisterInitializer implements Serializable {

    /**
     * @fields serialVersionUID : 串行号
     */
    private static final long serialVersionUID = 1600117147916204563L;

    @Autowired(required = false)
    private List<CacheRegister> cacheRegisters = Lists.newArrayList();

    public void init() {
        for (CacheRegister cacheRegister : cacheRegisters) {
            cacheRegister.loadAll();
            System.out.println(cacheRegister.toString());
        }
    }

}
