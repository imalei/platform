/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: ErrorInfoInitializer.java
 * @package org.platform.core.initialize.bean
 * @author leise
 * @date 2016年5月10日 下午2:15:14
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.initializer.bean;

import org.platform.core.constant.Constant;
import org.platform.core.system.flow.SysErrorInitFlow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @classname: ErrorInfoInitializer
 * @description: TODO(这里用一句话描述这个类的作用)
 */

@Component
public class ErrorInfoInitializer {

    private static final Logger logger = LoggerFactory.getLogger(ErrorInfoInitializer.class);

    @Autowired
    private SysErrorInitFlow sysErrorInitFlow;

    /**
     * @throws Exception 
     * 
     * @title: initService
     * @author leise
     * @description:
     * @date 2016年4月27日 下午12:52:32
     * @throws
     */
    public void init() throws Exception {
        sysErrorInitFlow.doService();
        logger.info("初始化系统错误信息:" + Constant.SYS_ERROR_MAP);
    }

}
