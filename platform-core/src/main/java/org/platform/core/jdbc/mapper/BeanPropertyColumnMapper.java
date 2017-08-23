/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: BeanFieldColumnMapper.java
 * @package org.platform.core.jdbc.mapper
 * @author leise
 * @date 2016年5月16日 下午8:46:28
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.jdbc.mapper;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

/**
 * @classname: BeanFieldColumnMapper
 * @description: TODO(这里用一句话描述这个类的作用)
 */

public class BeanPropertyColumnMapper<T> extends BeanPropertyRowMapper<T> {

    private Map<String, String> mappedColumns;

    /** Map of the fields we provide mapping for */
    private Map<String, PropertyDescriptor> mappedFields;

    /** Set of bean properties we provide mapping for */
    private Set<String> mappedProperties;

    /**
     * Create a new {@code BeanPropertyRowMapper} for bean-style configuration.
     * 
     * @see #setMappedClass
     * @see #setCheckFullyPopulated
     */
    public BeanPropertyColumnMapper() {
    }

    /**
     * Create a new {@code BeanPropertyRowMapper}, accepting unpopulated
     * properties in the target bean.
     * <p>
     * Consider using the {@link #newInstance} factory method instead, which
     * allows for specifying the mapped type once only.
     * 
     * @param mappedClass the class that each row should be mapped to
     */
    public BeanPropertyColumnMapper(Class<T> mappedClass) {
        super.initialize(mappedClass);
        this.initialize(mappedClass);
    }

    /**
     * Initialize the mapping metadata for the given class.
     * 
     * @param mappedClass the mapped class
     */
    public void initialize(Class<T> mappedClass) {
        this.mappedFields = new HashMap<String, PropertyDescriptor>();
        this.mappedProperties = new HashSet<String>();
        this.mappedColumns = new HashMap<String, String>();
        PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(mappedClass);
        for (PropertyDescriptor pd : pds) {
            if (pd.getWriteMethod() != null) {
                this.mappedFields.put(lowerCaseName(pd.getName()), pd);
                String underscoredName = underscoreName(pd.getName());
                if (!lowerCaseName(pd.getName()).equals(underscoredName)) {
                    this.mappedFields.put(underscoredName, pd);
                }
                this.mappedProperties.add(pd.getName());
                this.mappedColumns.put(pd.getName(), underscoredName);
            }
        }
    }

    /**
     * @return the mappedFields
     */

    public Map<String, PropertyDescriptor> getMappedFields() {
        return mappedFields;
    }

    /**
     * @param mappedFields the mappedFields to set
     */

    public void setMappedFields(Map<String, PropertyDescriptor> mappedFields) {
        this.mappedFields = mappedFields;
    }

    /**
     * @return the mappedProperties
     */

    public Set<String> getMappedProperties() {
        return mappedProperties;
    }

    /**
     * @param mappedProperties the mappedProperties to set
     */

    public void setMappedProperties(Set<String> mappedProperties) {
        this.mappedProperties = mappedProperties;
    }

    /**
     * @return the mappedColumns
     */

    public Map<String, String> getMappedColumns() {
        return mappedColumns;
    }

    /**
     * @param mappedColumns the mappedColumns to set
     */

    public void setMappedColumns(Map<String, String> mappedColumns) {
        this.mappedColumns = mappedColumns;
    }

}
