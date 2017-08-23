/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: JdbcEntityOperAction.java
 * @package org.platform.core.action.jdbc
 * @author leise
 * @date 2016年4月27日 下午7:43:32
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.action.jdbc;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.platform.core.action.enums.JdbcOpType;
import org.platform.core.annotation.action.JdbcEntityAction;
import org.platform.core.base.entity.Entity;
import org.platform.core.error.enums.ErrorCode;
import org.platform.core.error.exception.ExceptionBuilder;
import org.platform.core.error.exception.PFException;
import org.platform.core.jdbc.builder.JdbcSqlBuilder;
import org.platform.core.jdbc.context.JdbcSqlContext;
import org.platform.core.jdbc.transaction.TransactionManager;
import org.platform.core.utils.ActionAnnotationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * @classname: JdbcEntityOperAction
 * @description: TODO(这里用一句话描述这个类的作用)
 */
@Component
public class JdbcEntityBatchOperAction implements JdbcAction {

    private static final Logger logger = LoggerFactory.getLogger(JdbcEntityBatchOperAction.class);

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private TransactionManager transactionManager;

    public void execute(String actionId, String declareClassName, List<? extends Entity> entityList) throws PFException {

        logger.info("[执行action开始]--[actionId:{}]--[调用方:{}]", actionId, declareClassName);

        Assert.isTrue(CollectionUtils.isNotEmpty(entityList), "the entityList must be not null");

        JdbcEntityAction jdbcEntityAction = ActionAnnotationUtils.getAnnotation(declareClassName, actionId,
                JdbcEntityAction.class);
        Assert.notNull(jdbcEntityAction, "the JdbcEntityAction config must be not null");

        String actionName = jdbcEntityAction.name();
        logger.info("[actionId:{}]--[调用方:{}]--[actionName:{}]", actionId, declareClassName, actionName);

        Class<?> clazz = jdbcEntityAction.entityClass();
        Assert.isTrue(entityList.get(0).getClass().getName().equals(clazz.getName()), "the entity class type must be "
                + clazz.getName());

        JdbcOpType opType = jdbcEntityAction.opType();
        Assert.isTrue(opType == JdbcOpType.INSERT || opType == JdbcOpType.UPDATE || opType == JdbcOpType.DELETE,
                "the opType is [" + opType + "], please set the right opType");

        try {

            JdbcSqlContext sqlContext = null;

            switch (opType) {
                case INSERT:
                    sqlContext = JdbcSqlBuilder.buildBatchInsertSQL(entityList);
                    break;

                case UPDATE:
                    sqlContext = JdbcSqlBuilder.buildBatchUpdateSQL(entityList);
                    break;

                case DELETE:
                    sqlContext = JdbcSqlBuilder.buildBatchDeleteSQL(entityList);
                    break;

                default:
                    return;
            }

            String sql = sqlContext.getSql();
            logger.info("[actionId:{}]--[调用方:{}]--[执行sql(BATCH {}操作):{}]", actionId, declareClassName,
                    opType.toString(), sql);

            logger.debug("[actionId:{}]--[调用方:{}]--[输入参数:{}]", actionId, declareClassName,
                    String.valueOf(sqlContext.getValueMaps()));

            boolean newTransaction = jdbcEntityAction.newTransaction();
            transactionManager.begin(newTransaction);

            try {
                int[] result = namedParameterJdbcTemplate.batchUpdate(sql, sqlContext.getValueMaps());
                logger.info("[actionId:{}]--[调用方:{}]--[输出参数:{}]", actionId, declareClassName, result.length);
            }
            catch (Exception e) {
                transactionManager.rollback(newTransaction);
                throw ExceptionBuilder.newException().withError(ErrorCode.JDBC_BATCH_OPERATION_FAILED).withCause(e)
                        .build();
            }
            transactionManager.commit(newTransaction);
        }
        catch (Exception e) {
            throw ExceptionBuilder.newException().withError(ErrorCode.ACTION_EXECUTE_FAILD)
                    .withMessageArgs(new Object[] { "数据批量操作" }).withCause(e).build();
        }

        logger.info("[执行action结束]--[actionId:{}]--[调用方:{}]]", actionId, declareClassName);

    }

}
