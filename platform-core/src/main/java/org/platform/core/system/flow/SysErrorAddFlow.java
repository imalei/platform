/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: SysErrorAddFlow.java
 * @package org.platform.core.system.flow
 * @author leise
 * @date 2016年7月7日 下午8:39:34
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.system.flow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Validator;

import org.apache.commons.collections.CollectionUtils;
import org.platform.core.action.ActionConfigurable;
import org.platform.core.action.enums.JdbcOpType;
import org.platform.core.action.jdbc.JdbcEntityBatchOperAction;
import org.platform.core.action.jdbc.JdbcEntityQueryAction;
import org.platform.core.annotation.action.JdbcEntityAction;
import org.platform.core.annotation.service.PFService;
import org.platform.core.base.service.ApiService;
import org.platform.core.constant.Constant;
import org.platform.core.system.dto.SysErrorAddInput;
import org.platform.core.system.dto.SysErrorAddOutput;
import org.platform.core.system.entity.TSysError;
import org.platform.core.validator.BeanValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @classname: SysErrorAddFlow
 * @description: TODO(这里用一句话描述这个类的作用)
 */
@PFService(serviceDesc = "添加系统错误信息", serviceId = "SERR0002", serviceVersion = Constant.SERVICE_VERSION_DEFAULT)
public class SysErrorAddFlow extends ApiService<SysErrorAddInput, SysErrorAddOutput> {

    /**
     * @fields serialVersionUID : 串行号
     */
    private static final long serialVersionUID = 5004891354559078857L;
    
    /** 服务调用方类名 */
    private String declareClassName = ActionConfig.class.getName();

    @Autowired
    private JdbcEntityQueryAction jdbcEntityQueryAction;

    @Autowired
    private JdbcEntityBatchOperAction jdbcEntityBatchOperAction;

    @Autowired
    private Validator validator;

    @SuppressWarnings("unchecked")
    @Override
    protected SysErrorAddOutput doExecute(SysErrorAddInput input) throws Exception {

        BeanValidator.validateWithException(validator, input);

        List<TSysError> errors = input.getErrorList();
        List<TSysError> insertEntitys = new ArrayList<TSysError>();
        List<TSysError> updateEntitys = new ArrayList<TSysError>();

        List<TSysError> dbErrors = (List<TSysError>) jdbcEntityQueryAction.execute(ActionConfig.ERROR_QUERY,
                declareClassName, null);

        Map<String, TSysError> dbErrorsMap = new HashMap<String, TSysError>();

        if (CollectionUtils.isNotEmpty(dbErrors)) {
            for (TSysError dbError : dbErrors) {
                String errorNo = dbError.getErrorNo();
                dbErrorsMap.put(errorNo, dbError);
            }
        }

        for (TSysError error : errors) {
            String errorNo = error.getErrorNo();
            if (dbErrorsMap.containsKey(errorNo)) {
                updateEntitys.add(error);
            } else {
                insertEntitys.add(error);
            }
        }

        if (CollectionUtils.isNotEmpty(insertEntitys)) {
            jdbcEntityBatchOperAction.execute(ActionConfig.ERROR_INSERT, declareClassName, insertEntitys);
        }

        SysErrorAddOutput output = new SysErrorAddOutput();
        if (CollectionUtils.isNotEmpty(updateEntitys)) {
            String flag = input.getFlag();
            if ("2".equals(flag)) {
                jdbcEntityBatchOperAction.execute(ActionConfig.ERROR_UPDATE, declareClassName, updateEntitys);
            } else {
                output.setFailedErrorList(updateEntitys);
            }
        }

        return output;
    }

    @Component
    static class ActionConfig implements ActionConfigurable {

        @JdbcEntityAction(entityClass = TSysError.class, name = "查询系统错误信息列表", opType = JdbcOpType.QUERY)
        public static final String ERROR_QUERY = "ERROR_QUERY";

        @JdbcEntityAction(entityClass = TSysError.class, name = "批量添加系统错误信息", opType = JdbcOpType.INSERT)
        public static final String ERROR_INSERT = "ERROR_INSERT";

        @JdbcEntityAction(entityClass = TSysError.class, name = "批量更新系统错误信息", opType = JdbcOpType.UPDATE)
        public static final String ERROR_UPDATE = "ERROR_UPDATE";
    }

}
