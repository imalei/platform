/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: EntityRegister.java
 * @package org.platform.core.base.entity
 * @author leise
 * @date 2016年3月30日 下午4:33:13
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.initializer.register;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.ClassUtils;
import org.platform.core.annotation.entity.PK;
import org.platform.core.annotation.entity.Table;
import org.platform.core.base.entity.Entity;
import org.platform.core.jdbc.mapper.BeanPropertyColumnMapper;
import org.platform.core.strategy.NamedColumnStrategy;
import org.springframework.util.Assert;

import com.google.common.collect.Maps;

/**
 * @classname: EntityRegister
 * @description: TODO(这里用一句话描述这个类的作用)
 */
public class EntityRegister {

    private String className;

    private String tableName;

    private String pkColumn;

    private String pkField;

    private Map<String, String> pks = Maps.newHashMap();

    private BeanPropertyColumnMapper<? extends Entity> beanPropertyColumnMapper;

    public EntityRegister() {

    }

    @SuppressWarnings("unchecked")
    public EntityRegister(String entityClassName) {

        Class<? extends Entity> entityClass = null;
        try {
            entityClass = ClassUtils.getClass(entityClassName);
            initEntityRegister(entityClass);
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * @title: initEntityInfo
     * @author leise
     * @description:
     * @date 2016年7月15日 上午11:18:35
     * @throws
     */

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void initEntityRegister(Class<? extends Entity> entityClass) {
        Table table = entityClass.getAnnotation(Table.class);
        String tableName = table.value();
        String className = entityClass.getName();
        this.className = className;
        this.tableName = tableName.toLowerCase();

        Map<String, String> pks = new HashMap<String, String>();

        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(PK.class)) {
                String fieldName = field.getName();
                String columnName = NamedColumnStrategy.getDefaultColumnName(fieldName);
                this.pkColumn = columnName;
                this.pkField = fieldName;
                pks.put(fieldName, columnName);
            }
        }
        Assert.isTrue(!pks.isEmpty(), "entity initialize failed: the entity " + entityClass
                + " pk field must set @PK annotation");
        this.pks = pks;
        this.beanPropertyColumnMapper = new BeanPropertyColumnMapper(entityClass);

    }

    /**
     * @return the className
     */

    public String getClassName() {
        return className;
    }

    /**
     * @param className the className to set
     */

    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * @return the tableName
     */

    public String getTableName() {
        return tableName;
    }

    /**
     * @param tableName the tableName to set
     */

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * @return the beanPropertyColumnMapper
     */

    public BeanPropertyColumnMapper<? extends Entity> getBeanPropertyColumnMapper() {
        return beanPropertyColumnMapper;
    }

    /**
     * @param beanPropertyColumnMapper the beanPropertyColumnMapper to set
     */

    public void setBeanPropertyColumnMapper(BeanPropertyColumnMapper<? extends Entity> beanPropertyColumnMapper) {
        this.beanPropertyColumnMapper = beanPropertyColumnMapper;
    }

    /**
     * @return the pkColumn
     */

    public String getPkColumn() {
        return pkColumn;
    }

    /**
     * @param pkColumn the pkColumn to set
     */

    public void setPkColumn(String pkColumn) {
        this.pkColumn = pkColumn;
    }

    /**
     * @return the pkField
     */

    public String getPkField() {
        return pkField;
    }

    /**
     * @param pkField the pkField to set
     */

    public void setPkField(String pkField) {
        this.pkField = pkField;
    }

    /**
     * @return the pks
     */

    public Map<String, String> getPks() {
        return pks;
    }

    /**
     * @param pks the pks to set
     */

    public void setPks(Map<String, String> pks) {
        this.pks = pks;
    }

}
