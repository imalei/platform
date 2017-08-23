/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: TransactionJdbcTemplate.java
 * @package org.platform.core.jdbc.template
 * @author leise
 * @date 2016年4月28日 上午11:14:58
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.jdbc.transaction;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * @classname: TransactionJdbcTemplate
 * @description: TODO(这里用一句话描述这个类的作用)
 */
@Component
public class TransactionManager implements InitializingBean {

    private static Logger logger = LoggerFactory.getLogger(TransactionManager.class);

    private static final String GLOBAL_TRANSACTION = "globalTransaction";

    private static final String NEW_TRANSACTION = "newTransaction";

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    private DefaultTransactionDefinition transactionDefinition;

    private ThreadLocal<Map<String, TransactionStatus>> transcationThreadLocal = new ThreadLocal<Map<String, TransactionStatus>>();

    public void beginGlobalTransation() {

        transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = platformTransactionManager.getTransaction(transactionDefinition);
        Map<String, TransactionStatus> transactionMap = transcationThreadLocal.get();

        if (transactionMap == null) {
            transactionMap = new HashMap<String, TransactionStatus>();
        }
        if (transactionMap.get(GLOBAL_TRANSACTION) == null) {
            transactionMap.put(GLOBAL_TRANSACTION, status);
            transcationThreadLocal.set(transactionMap);
            logger.info("开启全局事务....");
        }

    }

    public void commitGlobalTransation() {
        TransactionStatus status = transcationThreadLocal.get().get(GLOBAL_TRANSACTION);
        if (status == null) {
            return;
        }

        platformTransactionManager.commit(status);
        transcationThreadLocal.get().put(GLOBAL_TRANSACTION, null);

        logger.info("提交全局事务....");
    }

    public void rollbackGlobalTransation() {
        TransactionStatus status = transcationThreadLocal.get().get(GLOBAL_TRANSACTION);
        if (status == null) {
            return;
        }

        platformTransactionManager.rollback(status);
        transcationThreadLocal.get().put(GLOBAL_TRANSACTION, null);
        logger.info("回滚全局事务....");
    }

    public void begin(Boolean newTransaction) throws Exception {

        if (Boolean.valueOf(String.valueOf(newTransaction))) {
            transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            TransactionStatus status = platformTransactionManager.getTransaction(transactionDefinition);
            Map<String, TransactionStatus> transactionMap = transcationThreadLocal.get();
            transactionMap.put(NEW_TRANSACTION, status);
            transcationThreadLocal.set(transactionMap);
            logger.info("开启独立事务....");
        }
    }

    public void commit(Boolean newTransaction){

        if (Boolean.valueOf(String.valueOf(newTransaction))) {
            TransactionStatus status = transcationThreadLocal.get().get(NEW_TRANSACTION);
            if (status == null) {
                return;
            }

            platformTransactionManager.commit(status);
            transcationThreadLocal.get().put(NEW_TRANSACTION, null);
            logger.info("提交独立事务....");
        }
    }

    public void rollback(Boolean newTransaction) {
        if (Boolean.valueOf(String.valueOf(newTransaction))) {
            TransactionStatus status = transcationThreadLocal.get().get(NEW_TRANSACTION);
            if (status == null) {
                return;
            }

            platformTransactionManager.rollback(status);
            transcationThreadLocal.get().put(NEW_TRANSACTION, null);
            logger.info("回滚独立事务....");
        }
    }

    @Override
    public void afterPropertiesSet() {
        transactionDefinition = new DefaultTransactionDefinition();
        transactionDefinition.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        Map<String, TransactionStatus> transationStatusMap = new HashMap<String, TransactionStatus>();
        transcationThreadLocal.set(transationStatusMap);
    }

    public PlatformTransactionManager getPlatformTransactionManager() {
        return platformTransactionManager;
    }

    public DefaultTransactionDefinition getTransactionDefinition() {
        return transactionDefinition;
    }

    public ThreadLocal<Map<String, TransactionStatus>> getTranscationThreadLocal() {
        return transcationThreadLocal;
    }

}
