/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: JdbcSqlQueryAction.java
 * @package org.platform.core.action.jdbc
 * @author leise
 * @date 2016年4月27日 下午7:43:32
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.action.jdbc;

import java.util.List;

import org.platform.core.action.enums.JdbcOpType;
import org.platform.core.annotation.action.JdbcEntityAction;
import org.platform.core.base.entity.Entity;
import org.platform.core.error.enums.ErrorCode;
import org.platform.core.error.exception.ExceptionBuilder;
import org.platform.core.error.exception.PFException;
import org.platform.core.jdbc.builder.JdbcSqlBuilder;
import org.platform.core.jdbc.context.JdbcSqlContext;
import org.platform.core.jdbc.mapper.BeanPropertyColumnMapper;
import org.platform.core.jdbc.page.PageParams;
import org.platform.core.utils.ActionAnnotationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * @classname: JdbcSqlQueryAction
 * @description: TODO(这里用一句话描述这个类的作用)
 */
@Component
public class JdbcEntityPageQueryAction implements JdbcAction {

    private static final Logger logger = LoggerFactory.getLogger(JdbcEntityPageQueryAction.class);

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public List<?> execute(String actionId, String declareClassName, Entity entity, PageParams pageParams)
            throws PFException {

        logger.info("[执行action开始]--[actionId:{}]--[调用方:{}]", actionId, declareClassName);

        JdbcEntityAction jdbcEntityAction = ActionAnnotationUtils.getAnnotation(declareClassName, actionId, JdbcEntityAction.class);
        Assert.notNull(jdbcEntityAction, "the JdbcEntityAction config must be not null");

        String actionName = jdbcEntityAction.name();
        logger.info("[actionId:{}]--[调用方:{}]--[actionName:{}]", actionId, declareClassName, actionName);

        JdbcOpType opType = jdbcEntityAction.opType();
        Assert.isTrue(opType == JdbcOpType.QUERY, "please set the right opType: " + JdbcOpType.QUERY);

        Class<?> entityClass = jdbcEntityAction.entityClass();
        if (entity == null) {
            entity = (Entity) BeanUtils.instantiateClass(entityClass);
        } else {
            Assert.isTrue(entity.getClass().getName().equals(entityClass.getName()), "the entity class type must be "
                    + entityClass.getName());
        }

        List<?> result = null;

        try {

            JdbcSqlContext countSqlContext = JdbcSqlBuilder.buildTotalCountQuerySQL(entity);
            String countSql = countSqlContext.getSql();

            logger.info("[actionId:{}]--[调用方:{}]--[执行sql(查询数据总记录数):{}]", actionId, declareClassName, countSql);
            Integer totalCount = namedParameterJdbcTemplate.queryForObject(countSql, countSqlContext.getValueMaps()[0],
                    Integer.class);
            if (totalCount == 0) {
                logger.info("[actionId:{}]--[调用方:{}]--[输出参数:{}]", actionId, declareClassName, totalCount);
                logger.info("[执行action结束]--[actionId:{}]--[调用方:{}]]", actionId, declareClassName);
                return null;
            }

            int pageSize = pageParams.getPageSize();
            int totalPage = this.getTotalPage(totalCount, pageSize);
            pageParams.setTotalCount(totalCount);
            pageParams.setTotalPage(totalPage);

            JdbcSqlContext sqlContext = JdbcSqlBuilder.buildPageQuerySQL(entity, pageParams);
            String pageSql = sqlContext.getSql();
            logger.info("[actionId:{}]--[调用方:{}]--[执行sql(分页查询操作):{}]", actionId, declareClassName, pageSql);
            logger.info("[actionId:{}]--[调用方:{}]--[输入参数:{}]", actionId, declareClassName,
                    String.valueOf(sqlContext.getValueMaps()[0]));

            try {
                result = namedParameterJdbcTemplate.query(pageSql, sqlContext.getValueMaps()[0],
                        new BeanPropertyColumnMapper(entityClass));
                logger.info("[actionId:{}]--[调用方:{}]--[输出参数:{}]", actionId, declareClassName, result.size());
            }
            catch (Exception e) {
                throw ExceptionBuilder.newException().withError(ErrorCode.JDBC_PAGE_QUERY_FAILED).withCause(e).build();
            }
        }
        catch (Exception e) {
            throw ExceptionBuilder.newException().withError(ErrorCode.ACTION_EXECUTE_FAILD)
                    .withMessageArgs(new Object[] { "数据分页查询" }).withCause(e).build();
        }

        logger.info("[执行action结束]--[actionId:{}]--[调用方:{}]]", actionId, declareClassName);

        return result;

    }

    public int getTotalPage(int totalCount, int pageSize) {
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        return totalPage;
    }
}
