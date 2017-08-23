/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: JdbcSqlOperAction.java
 * @package org.platform.core.action.jdbc
 * @author leise
 * @date 2016年4月27日 下午7:43:32
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.action.jdbc;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.platform.core.annotation.action.JdbcSqlAction;
import org.platform.core.error.enums.ErrorCode;
import org.platform.core.error.exception.ExceptionBuilder;
import org.platform.core.error.exception.PFException;
import org.platform.core.jdbc.transaction.TransactionManager;
import org.platform.core.utils.ActionAnnotationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * @classname: JdbcSqlOperAction
 * @description: TODO(这里用一句话描述这个类的作用)
 */
@Component
public class JdbcSqlOperAction implements JdbcAction {

    private static final Logger logger = LoggerFactory.getLogger(JdbcSqlOperAction.class);

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private TransactionManager transactionManager;

    public void execute(String actionId, String declareClassName, Map<String, Object> params) throws PFException {

        logger.info("[执行action开始]--[actionId:{}]--[调用方:{}]", actionId, declareClassName);
        
        JdbcSqlAction jdbcSqlAction = ActionAnnotationUtils.getAnnotation(declareClassName, actionId,
                JdbcSqlAction.class);
        Assert.notNull(jdbcSqlAction, "the JdbcSqlAction config must be not null");
        
        String actionName = jdbcSqlAction.name();
        logger.info("[actionId:{}]--[调用方:{}]--[actionName:{}]", actionId, declareClassName, actionName);
        
        String sql = jdbcSqlAction.sql();
        Assert.isTrue(StringUtils.isNotBlank(sql), "the sql must be not blank");
        String opType = sql.trim().substring(0, 6).toUpperCase();
        logger.info("[actionId:{}]--[调用方:{}]--[执行sql({}操作):{}]", actionId, declareClassName, opType, sql);
        logger.info("[actionId:{}]--[调用方:{}]--[输入参数:{}]", actionId, declareClassName, String.valueOf(params));

        try {

            boolean newTransaction = jdbcSqlAction.newTransaction();
            transactionManager.begin(newTransaction);

            try {
                int result = namedParameterJdbcTemplate.update(sql, params);
                logger.info("[actionId:{}]--[调用方:{}]--[输出参数:{}]", actionId, declareClassName, result);
            }
            catch (Exception e) {
                transactionManager.rollback(newTransaction);
                throw ExceptionBuilder.newException().withError(ErrorCode.JDBC_SQL_OPERATION_FAILED).withCause(e)
                        .build();
            }
            transactionManager.commit(newTransaction);
        }
        catch (Exception e) {
            throw ExceptionBuilder.newException().withError(ErrorCode.ACTION_EXECUTE_FAILD)
                    .withMessageArgs(new Object[] { "数据操作(S)" }).withCause(e).build();
        }

        logger.info("[执行action结束]--[actionId:{}]--[调用方:{}]]", actionId, declareClassName);

    }
}
