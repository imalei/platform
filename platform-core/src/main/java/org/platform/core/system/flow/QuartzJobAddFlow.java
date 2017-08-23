/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: QuartzJobAddFlow.java
 * @package org.platform.core.system.flow
 * @author leise
 * @date 2016年7月7日 下午8:39:34
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.system.flow;

import org.platform.core.action.ActionConfigurable;
import org.platform.core.action.enums.JdbcOpType;
import org.platform.core.action.jdbc.JdbcEntitySingleOperAction;
import org.platform.core.annotation.action.JdbcEntityAction;
import org.platform.core.annotation.service.PFService;
import org.platform.core.base.service.ApiService;
import org.platform.core.constant.Constant;
import org.platform.core.error.exception.PFException;
import org.platform.core.system.dto.QuartzJobAddInput;
import org.platform.core.system.dto.QuartzJobAddOutput;
import org.platform.core.system.entity.TQuartzJob;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @classname: QuartzJobAddFlow
 * @description: TODO(这里用一句话描述这个类的作用)
 */
@PFService(serviceDesc = "定时任务添加", serviceId = "QRTZ0002", serviceVersion = Constant.SERVICE_VERSION_DEFAULT)
public class QuartzJobAddFlow extends ApiService<QuartzJobAddInput, QuartzJobAddOutput> {

    /**
     * @fields serialVersionUID : 串行号
     */

    private static final long serialVersionUID = 5004891354559078857L;

    /** 服务调用方类名 */
    private String declareClassName = ActionConfig.class.getName();

    @Autowired
    private JdbcEntitySingleOperAction jdbcEntitySingleOperAction;

    @Override
    protected QuartzJobAddOutput doExecute(QuartzJobAddInput input) throws PFException {
        TQuartzJob entity = new TQuartzJob();
        BeanUtils.copyProperties(input, entity);
        jdbcEntitySingleOperAction.execute(ActionConfig.QUARTZ_JOB_INSERT, declareClassName, entity);
        QuartzJobAddOutput output = new QuartzJobAddOutput();
        return output;
    }

    @Component
    private static class ActionConfig implements ActionConfigurable {

        @JdbcEntityAction(entityClass = TQuartzJob.class, name = "新增定时任务", opType = JdbcOpType.INSERT)
        public static final String QUARTZ_JOB_INSERT = "QUARTZ_JOB_INSERT";
    }

}
