/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: SystemService.java
 * @package org.platform.core.schedule.service
 * @author leise
 * @date 2016年7月5日 下午7:15:37
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.base.service;

import org.platform.core.error.enums.ErrorCode;
import org.platform.core.error.exception.ExceptionBuilder;
import org.platform.core.jdbc.transaction.TransactionManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @classname: SystemService
 * @description: TODO(这里用一句话描述这个类的作用)
 */

public abstract class SystemService implements Service {

    /**
     * @fields serialVersionUID : 串行号
     */

    private static final long serialVersionUID = -639431225539006262L;

    @Autowired
    private TransactionManager transactionManager;

    protected abstract void doExecute();

    /**
     * 接口服务实现方法
     */
    public void doService() {

        transactionManager.beginGlobalTransation();
        try {
            doExecute();
        }
        catch (Exception e) {
            transactionManager.rollbackGlobalTransation();
            e.printStackTrace();
            throw ExceptionBuilder.newException().withError(ErrorCode.SERVICE_EXECUTE_FAILED).withCause(e).build();
        }
        transactionManager.commitGlobalTransation();

    }
}
