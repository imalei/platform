/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: SysErrorInitFlow.java
 * @package org.platform.core.system.flow
 * @author leise
 * @date 2016年7月8日 下午6:51:59
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.system.flow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.platform.core.action.ActionConfigurable;
import org.platform.core.action.enums.JdbcOpType;
import org.platform.core.action.jdbc.JdbcEntityQueryAction;
import org.platform.core.annotation.action.JdbcEntityAction;
import org.platform.core.annotation.service.PFService;
import org.platform.core.base.service.SystemService;
import org.platform.core.constant.Constant;
import org.platform.core.system.entity.TSysError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @classname: SysErrorInitFlow
 * @description: TODO(这里用一句话描述这个类的作用)
 */
@PFService(serviceDesc = "系统错误信息初始化", serviceId = "SERR0001", serviceVersion = Constant.SERVICE_VERSION_DEFAULT, serviceEnable = false)
public class SysErrorInitFlow extends SystemService {

    /**
     * @fields serialVersionUID : 串行号
     */
    private static final long serialVersionUID = -3536584035238561230L;

    /** 服务调用方类名 */
    private String declareClassName = ActionConfig.class.getName();

    @Autowired
    private JdbcEntityQueryAction jdbcEntityQueryAction;

    @SuppressWarnings("unchecked")
    @Override
    protected void doExecute() {

        List<TSysError> errorInfos = (List<TSysError>) jdbcEntityQueryAction.execute(ActionConfig.QUERY_SYS_ERROR,
                declareClassName, null);

        Map<String, TSysError> errorMap = new HashMap<String, TSysError>();
        for (TSysError errorInfo : errorInfos) {
            String errorNo = errorInfo.getErrorNo();
            errorMap.put(errorNo, errorInfo);
        }
        Constant.SYS_ERROR_MAP.putAll(errorMap);
        errorMap.clear();

    }

    @Component
    private static class ActionConfig implements ActionConfigurable {

        @JdbcEntityAction(name = "查询系统错误信息列表", entityClass = TSysError.class, opType = JdbcOpType.QUERY)
        public static final String QUERY_SYS_ERROR = "QUERY_SYS_ERROR";
    }

}
