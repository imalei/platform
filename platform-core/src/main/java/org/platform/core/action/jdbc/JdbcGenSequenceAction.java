/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: JdbcSequenceGenAction.java
 * @package org.platform.core.action.jdbc
 * @author leise
 * @date 2016年7月8日 下午7:33:37
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.action.jdbc;

import org.apache.commons.lang.StringUtils;
import org.platform.core.annotation.action.JdbcGenSeqNoAction;
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
 * @classname: JdbcSequenceGenAction
 * @description: TODO(这里用一句话描述这个类的作用)
 */
@Component
public class JdbcGenSequenceAction implements JdbcAction {

    private static final Logger logger = LoggerFactory.getLogger(JdbcEntitySingleOperAction.class);

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private TransactionManager transactionManager;

    public String execute(String actionId, String declareClassName) throws PFException {

        logger.info("[执行action开始]--[actionId:{}]--[调用方:{}]", actionId, declareClassName);

        JdbcGenSeqNoAction jdbcGenSeqNoAction = ActionAnnotationUtils.getAnnotation(declareClassName, actionId,
                JdbcGenSeqNoAction.class);
        Assert.notNull(jdbcGenSeqNoAction, "the JdbcGenSeqNoAction config must be not null");

        String actionName = jdbcGenSeqNoAction.name();
        logger.info("[执行action开始]--[actionId:{}]--[actionName:{}]", actionId, declareClassName, actionName);

        String seqName = jdbcGenSeqNoAction.seqName();
        Assert.isTrue(StringUtils.isNotBlank(seqName), "the seqName must be not blank");

        String sql = "select " + seqName + ".nextval from dual";
        logger.info("[执行action开始]--[actionId:{}]--[执行sql(查询序列号):{}]", actionId, declareClassName, sql);

        String seqNo = null;
        try {
            boolean newTransaction = jdbcGenSeqNoAction.newTransaction();
            transactionManager.begin(newTransaction);

            try {
                seqNo = namedParameterJdbcTemplate.getJdbcOperations().queryForObject(sql, String.class);
                logger.info("[执行action开始]--[actionId:{}]--[输出参数:{}]", actionId, declareClassName, seqNo);
            }
            catch (Exception e) {
                transactionManager.rollback(newTransaction);
                throw ExceptionBuilder.newException().withError(ErrorCode.JDBC_GENSEQ_FAILED).withCause(e).build();
            }
            transactionManager.commit(newTransaction);
        }
        catch (Exception e) {
            throw ExceptionBuilder.newException().withError(ErrorCode.ACTION_EXECUTE_FAILD)
                    .withMessageArgs(new Object[] { "序列号生成" }).withCause(e).build();
        }

        logger.info("[执行action结束]--[actionId:{}]--[调用方:{}]]", actionId, declareClassName);

        return seqNo;

    }

}
