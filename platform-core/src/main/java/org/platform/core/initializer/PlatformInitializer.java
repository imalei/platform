/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: PlatformInitializer.java
 * @package org.platform.core.base.entity
 * @author leise
 * @date 2016年3月30日 下午4:33:13
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.initializer;

import java.util.List;
import java.util.Map;

import org.platform.core.a.test.TestService;
import org.platform.core.action.ActionConfigurable;
import org.platform.core.base.entity.Entity;
import org.platform.core.base.service.Service;
import org.platform.core.cache.redis.ErrorCache;
import org.platform.core.constant.Constant;
import org.platform.core.error.enums.ErrorCode;
import org.platform.core.initializer.bean.CacheRegisterInitializer;
import org.platform.core.initializer.bean.ErrorInfoInitializer;
import org.platform.core.initializer.bean.ServiceInitializer;
import org.platform.core.system.entity.TSysError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * @classname: PlatformInitialize
 * @description: TODO(这里用一句话描述这个类的作用)
 */
@Configuration
@Lazy(false)
public class PlatformInitializer implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(PlatformInitializer.class);

    @Autowired
    private List<Entity> entitys;

    @Autowired
    private List<Service> services;
    
    @Autowired
    private Map<String, Service> serviceMap;

    @Autowired
    private List<ActionConfigurable> actionConfigurables;

    @Autowired
    private TestService testService;

    @Autowired
    private ServiceInitializer serviceInitializer;

    @Autowired
    private ErrorInfoInitializer errorInfoInitializer;

    @Autowired
    private CacheRegisterInitializer cacheRegisterInitializer;

    @Autowired
    private ErrorCache errorCache;

    /* 
    * title: afterPropertiesSet
    * description: 
    * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
    */

    @Override
    public void afterPropertiesSet() throws Exception {

        // TODO Auto-generated method stub
        logger.info("系统初始化开始...............................................");
        setServiceList(services);
        serviceInitializer.init();
        errorInfoInitializer.init();
        cacheRegisterInitializer.init();
        logger.info("系统初始化结束...............................................");

        TSysError error = errorCache.get(ErrorCode.ACTION_EXECUTE_FAILD.getErrorNo());
        System.out.println(error.getErrorCode() + ":" + error.getErrorMessage());

        TSysError error1 = errorCache.get(ErrorCode.ACTION_EXECUTE_FAILD.getErrorNo());
        System.out.println(error1.getErrorCode() + ":" + error1.getErrorMessage());

        // testService.doService(null);

    }

    private void setServiceList(List<Service> services) {
        Constant.SYS_SERVICE_LIST = services;
    };

}
