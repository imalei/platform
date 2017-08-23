/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: AbstractService.java
 * @package org.platform.core.base.service
 * @author leise
 * @date 2015-8-25 下午3:32:22
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.base.service;

import static org.platform.core.utils.GenericUtils.getGenericTypeFromSuperclass;

import org.platform.core.base.dto.Input;
import org.platform.core.base.dto.Output;
import org.platform.core.error.enums.ErrorCode;
import org.platform.core.error.exception.ExceptionBuilder;
import org.platform.core.jdbc.transaction.TransactionManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @classname: AbstractService
 * @description: TODO(这里用一句话描述这个类的作用)
 */

public abstract class ApiService<I extends Input, O extends Output> implements Service {

    /**
     * @fields serialVersionUID : 串行号
     */

    private static final long serialVersionUID = -5417813518948609289L;

    @Autowired
    private TransactionManager transactionManager;

    protected abstract O doExecute(I input) throws Exception;

    /**
     * 接口服务实现方法
     */
    @SuppressWarnings("unchecked")
    public O doService(Input input) {

        transactionManager.beginGlobalTransation();
        O output = null;
        try {
            output = doExecute((I) input);
        }
        catch (Exception e) {
            transactionManager.rollbackGlobalTransation();
            e.printStackTrace();
            throw ExceptionBuilder.newException().withError(ErrorCode.SERVICE_EXECUTE_FAILED).withCause(e).build();
        }
        transactionManager.commitGlobalTransation();

        return output;
    }

    /**
     * 
     * @title: getInputClass
     * @author
     * @description: 获取接口定义服务输入类对象
     * @date 2015-8-29 下午7:10:57
     * @return
     * @throws
     */
    @SuppressWarnings("unchecked")
    protected final Class<I> getInputClass() {
        return getGenericTypeFromSuperclass(getClass(), 0);
    }

    /**
     * 
     * @title: getOutputClass
     * @author
     * @description: 获取接口定义服务输出类对象
     * @date 2015-8-29 下午7:10:57
     * @return
     * @throws
     */
    @SuppressWarnings("unchecked")
    protected final Class<O> getOutputClass() {
        return getGenericTypeFromSuperclass(getClass(), 1);
    }

    /**
     * 
     * @title: getInputClassName
     * @author
     * @description: 获取接口定义服务输入类名称
     * @date 2015-8-29 下午7:13:20
     * @return
     * @throws
     */
    public String getInputClassName() {
        return getInputClass().getName();
    }

    /**
     * 
     * @title: getOutputClassName
     * @author
     * @description: 获取接口定义服务输出类名称
     * @date 2015-8-29 下午7:13:27
     * @return
     * @throws
     */
    public String getOutputClassName() {
        return getOutputClass().getName();
    }

}
