/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: JdbcSqlBuilder.java
 * @package org.platform.core.builder
 * @author leise
 * @date 2016年4月13日 下午3:01:55
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.jdbc.builder;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.MapUtils;
import org.platform.core.base.entity.Entity;
import org.platform.core.cache.register.EntityCacheRegister;
import org.platform.core.initializer.register.EntityRegister;
import org.platform.core.jdbc.context.JdbcSqlContext;
import org.platform.core.jdbc.mapper.BeanPropertyColumnMapper;
import org.platform.core.jdbc.page.PageParams;

/**
 * @classname: JdbcSqlBuilder
 * @description: TODO(这里用一句话描述这个类的作用)
 */

public class JdbcSqlBuilder {
    
    
    private static final String CREATE_TIME_KEY = "createTime";
    
    private static final String MODIFY_TIME_KEY = "modifyTime";

    /**
     * 
     * @title: buildQuerySQL
     * @author leise
     * @description:
     * @date 2016年4月14日 上午11:00:56
     * @throws
     */
    public static JdbcSqlContext buildQuerySQL(Entity entity) {

        Class<? extends Entity> clazz = entity.getClass();
        String classFullName = clazz.getName();
        EntityRegister entityRegister = EntityCacheRegister.get(classFullName);
        String tableName = entityRegister.getTableName();

        BeanPropertyColumnMapper<? extends Entity> mapper = entityRegister.getBeanPropertyColumnMapper();
        Map<String, String> mappedColumns = mapper.getMappedColumns();
        Set<String> keys = mapper.getMappedProperties();
        keys.remove(CREATE_TIME_KEY);
        keys.remove(MODIFY_TIME_KEY);
        Map<String, PropertyDescriptor> mappedFields = mapper.getMappedFields();

        StringBuffer sqlBuffer = new StringBuffer();
        StringBuffer argsBuffer = new StringBuffer();
        sqlBuffer.append("select ");

        Map<String, Object> valueMap = new HashMap<String, Object>();

        for (String key : keys) {
            
            sqlBuffer.append(mappedColumns.get(key));
            sqlBuffer.append(",");
            try {
                PropertyDescriptor pd = mappedFields.get(key.toLowerCase());
                Method getMethod = pd.getReadMethod();
                if (getMethod != null) {
                    Object obj = getMethod.invoke(entity);
                    if (obj == null) {
                        continue;
                    }
                    argsBuffer.append(" and ");
                    argsBuffer.append(mappedColumns.get(key));
                    argsBuffer.append("=:");
                    argsBuffer.append(key);
                    valueMap.put(key, obj);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
        sqlBuffer.append("create_time, modify_time");
        sqlBuffer.append(" from ");
        sqlBuffer.append(tableName);
        sqlBuffer.append(" where 1=1");
        sqlBuffer.append(argsBuffer);

        Map<String, Object>[] valueMaps = new HashMap[1];
        valueMaps[0] = valueMap;

        return new JdbcSqlContext(sqlBuffer.toString(), valueMaps);
    }

    /**
     * 
     * @title: buildTotalCountQuerySQL
     * @author leise
     * @description:
     * @date 2016年4月14日 上午11:00:56
     * @throws
     */
    public static JdbcSqlContext buildTotalCountQuerySQL(Entity entity) {

        JdbcSqlContext sqlContext = buildQuerySQL(entity);
        String sql = sqlContext.getSql();
        StringBuffer countSqlBuffer = new StringBuffer();
        countSqlBuffer.append("select count(1) from (");
        countSqlBuffer.append(sql);
        countSqlBuffer.append(" ) temp");

        return new JdbcSqlContext(countSqlBuffer.toString(), sqlContext.getValueMaps());
    }

    /**
     * @title: buildTotalCountQuerySQL
     * @author leise
     * @description:
     * @date 2016年5月27日 下午1:47:02
     * @throws
     */
    public static JdbcSqlContext buildTotalCountQuerySQL(String querySql) {

        StringBuffer totalCountSqlBuffer = new StringBuffer();
        totalCountSqlBuffer.append("select count(1) from (");
        totalCountSqlBuffer.append(querySql);
        totalCountSqlBuffer.append(" ) temp");

        return new JdbcSqlContext(totalCountSqlBuffer.toString());
    }

    /**
     * 
     * @title: buildPageQuerySQL
     * @author leise
     * @description:
     * @date 2016年4月14日 上午11:00:56
     * @throws
     */
    public static JdbcSqlContext buildPageQuerySQL(Entity entity, PageParams pageParams) {

        int pageSize = pageParams.getPageSize();
        int currentPage = pageParams.getCurrentPage();

        int startRowId = (currentPage - 1) * pageSize;
        int endRowId = currentPage * pageSize;

        JdbcSqlContext sqlContext = buildQuerySQL(entity);
        String sql = sqlContext.getSql();

        StringBuffer pageSqlBuffer = new StringBuffer();
        pageSqlBuffer.append("select * from ( select temp.* ,rownum as rid from ( ");
        pageSqlBuffer.append(sql);
        
        Map<String, String> sort = pageParams.getSort();
        StringBuffer sortSqlBuffer = new StringBuffer();
        if (MapUtils.isNotEmpty(sort)) {
            Set<String> keySet = sort.keySet();
            for (String key : keySet) {
                sortSqlBuffer.append(" order by ");
                sortSqlBuffer.append(key);
                sortSqlBuffer.append(" ");
                sortSqlBuffer.append(sort.get(key));
                sortSqlBuffer.append(",");
            }
            String sortSql = sortSqlBuffer.substring(0, sortSqlBuffer.length()-1);
            pageSqlBuffer.append(sortSql);
        }
        
        pageSqlBuffer.append(" ) temp ) where 1=1");
        pageSqlBuffer.append(" and rid <=");
        pageSqlBuffer.append(endRowId);
        pageSqlBuffer.append(" and rid >");
        pageSqlBuffer.append(startRowId);

        return new JdbcSqlContext(pageSqlBuffer.toString(), sqlContext.getValueMaps(), pageParams);
    }

    /**
     * 
     * @title: buildPageQuerySQL
     * @author leise
     * @description:
     * @date 2016年4月14日 上午11:00:56
     * @throws
     */
    public static JdbcSqlContext buildPageQuerySQL(String querySql, PageParams pageParams) {

        int pageSize = pageParams.getPageSize();
        int currentPage = pageParams.getCurrentPage();

        int startRowId = (currentPage - 1) * pageSize;
        int endRowId = currentPage * pageSize;

        StringBuffer pageSqlBuffer = new StringBuffer();
        pageSqlBuffer.append("select * from ( select temp.* ,rownum as rid from ( ");
        pageSqlBuffer.append(querySql);
        pageSqlBuffer.append(" ) temp ) where 1=1");
        pageSqlBuffer.append(" and rid <=");
        pageSqlBuffer.append(endRowId);
        pageSqlBuffer.append(" and rid >");
        pageSqlBuffer.append(startRowId);

        return new JdbcSqlContext(pageSqlBuffer.toString());
    }

    /**
     * 
     * @title: buildInsertSQL
     * @author leise
     * @description:
     * @date 2016年4月14日 上午11:00:56
     * @throws
     */
    public static JdbcSqlContext buildInsertSQL(Entity entity) {

        Class<? extends Entity> clazz = entity.getClass();
        String classFullName = clazz.getName();
        EntityRegister entityRegister = EntityCacheRegister.get(classFullName);
        String tableName = entityRegister.getTableName();

        BeanPropertyColumnMapper<? extends Entity> mapper = entityRegister.getBeanPropertyColumnMapper();
        Map<String, String> mappedColumns = mapper.getMappedColumns();
        Set<String> keys = mapper.getMappedProperties();
        keys.remove(CREATE_TIME_KEY);
        keys.remove(MODIFY_TIME_KEY);
        Map<String, PropertyDescriptor> mappedFields = mapper.getMappedFields();

        StringBuffer sqlBuffer = new StringBuffer();
        StringBuffer argsBuffer = new StringBuffer();
        sqlBuffer.append("insert into ");
        sqlBuffer.append(tableName);
        sqlBuffer.append(" (");
        argsBuffer.append("(");

        Map<String, Object> valueMap = new HashMap<String, Object>();

        for (String key : keys) {
            try {
                
                PropertyDescriptor pd = mappedFields.get(key.toLowerCase());
                Method getMethod = pd.getReadMethod();

                if (getMethod != null) {
                    Object obj = getMethod.invoke(entity);
                    if (obj == null) {
                        continue;
                    }
                    sqlBuffer.append(mappedColumns.get(key));
                    sqlBuffer.append(",");
                    argsBuffer.append(":").append(key);
                    argsBuffer.append(",");
                    valueMap.put(key, obj);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }

        sqlBuffer.append("create_time");
        sqlBuffer.append(",");
        sqlBuffer.append("modify_time");
        sqlBuffer.append(") ");
        sqlBuffer.append("values ");

        argsBuffer.append("systimestamp");
        argsBuffer.append(",");
        argsBuffer.append("systimestamp");
        argsBuffer.append(")");

        sqlBuffer.append(argsBuffer);

        Map<String, Object>[] valueMaps = new HashMap[1];
        valueMaps[0] = valueMap;

        return new JdbcSqlContext(sqlBuffer.toString(), valueMaps);
    }

    /**
     * 
     * @title: buildBatchInsertSQL
     * @author leise
     * @description:
     * @date 2016年4月14日 上午11:00:56
     * @throws
     */
    public static JdbcSqlContext buildBatchInsertSQL(List<? extends Entity> entityList) {

        Entity entity = entityList.get(0);

        JdbcSqlContext sqlContext = buildInsertSQL(entity);
        String batchInsertSql = sqlContext.getSql();

        Map<String, Object>[] valueMaps = new HashMap[entityList.size()];

        Class<? extends Entity> clazz = entity.getClass();
        String classFullName = clazz.getName();
        EntityRegister entityRegister = EntityCacheRegister.get(classFullName);

        BeanPropertyColumnMapper<? extends Entity> mapper = entityRegister.getBeanPropertyColumnMapper();
        Set<String> keys = mapper.getMappedProperties();
        keys.remove(CREATE_TIME_KEY);
        keys.remove(MODIFY_TIME_KEY);
        Map<String, PropertyDescriptor> mappedFields = mapper.getMappedFields();

        for (int i = 0; i < entityList.size(); i++) {

            Entity e = entityList.get(i);
            Map<String, Object> valueMap = new HashMap<String, Object>();

            for (String key : keys) {
                try {
                    
                    PropertyDescriptor pd = mappedFields.get(key.toLowerCase());
                    Method getMethod = pd.getReadMethod();
                    if (getMethod != null) {
                        Object obj = getMethod.invoke(e);
                        if (obj == null) {
                            continue;
                        }
                        valueMap.put(key, obj);
                    }
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                    continue;
                }
            }

            valueMaps[i] = valueMap;
        }

        return new JdbcSqlContext(batchInsertSql, valueMaps);
    }

    /**
     * 
     * @title: buildUpdateSQL
     * @author leise
     * @description:
     * @date 2016年4月14日 上午11:29:31
     * @throws
     */
    public static JdbcSqlContext buildUpdateSQL(Entity entity) {

        Class<? extends Entity> clazz = entity.getClass();
        String classFullName = clazz.getName();
        EntityRegister entityRegister = EntityCacheRegister.get(classFullName);
        String tableName = entityRegister.getTableName();
        Map<String, String> pks = entityRegister.getPks();

        BeanPropertyColumnMapper<? extends Entity> mapper = entityRegister.getBeanPropertyColumnMapper();
        Map<String, String> mappedColumns = mapper.getMappedColumns();
        Set<String> keys = mapper.getMappedProperties();
        keys.remove(CREATE_TIME_KEY);
        keys.remove(MODIFY_TIME_KEY);
        Map<String, PropertyDescriptor> mappedFields = mapper.getMappedFields();

        StringBuffer sqlBuffer = new StringBuffer();
        StringBuffer argsBuffer = new StringBuffer();
        sqlBuffer.append("update ");
        sqlBuffer.append(tableName);
        sqlBuffer.append(" set ");

        Map<String, Object> valueMap = new HashMap<String, Object>();

        for (String key : keys) {
            try {
                
                PropertyDescriptor pd = mappedFields.get(key.toLowerCase());
                Method getMethod = pd.getReadMethod();

                if (getMethod != null) {
                    Object obj = getMethod.invoke(entity);
                    if (obj == null) {
                        continue;
                    }

                    if (pks.containsKey(key)) {
                        String columnName = pks.get(key);
                        argsBuffer.append(" and ");
                        argsBuffer.append(columnName);
                        argsBuffer.append("=:");
                        argsBuffer.append(key);
                        valueMap.put(key, obj);
                        continue;
                    }

                    String columnName = mappedColumns.get(key);
                    sqlBuffer.append(columnName);
                    sqlBuffer.append("=:").append(key);
                    sqlBuffer.append(",");
                    valueMap.put(key, obj);
                }

            }
            catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }

        sqlBuffer.append("modify_time=systimestamp");
        sqlBuffer.append(" where 1=1");
        sqlBuffer.append(argsBuffer);

        Map<String, Object>[] valueMaps = new HashMap[1];
        valueMaps[0] = valueMap;

        return new JdbcSqlContext(sqlBuffer.toString(), valueMaps);
    }

    /**
     * 
     * @title: buildBatchUpdateSQL
     * @author leise
     * @description:
     * @date 2016年4月14日 上午11:29:31
     * @throws
     */
    public static JdbcSqlContext buildBatchUpdateSQL(List<? extends Entity> entityList) {

        Entity entity = entityList.get(0);
        JdbcSqlContext sqlContext = buildUpdateSQL(entity);
        String batchUpdateSql = sqlContext.getSql();

        Class<? extends Entity> clazz = entity.getClass();
        String classFullName = clazz.getName();
        EntityRegister entityRegister = EntityCacheRegister.get(classFullName);

        BeanPropertyColumnMapper<? extends Entity> mapper = entityRegister.getBeanPropertyColumnMapper();
        Set<String> keys = mapper.getMappedProperties();
        keys.remove(CREATE_TIME_KEY);
        keys.remove(MODIFY_TIME_KEY);
        Map<String, PropertyDescriptor> mappedFields = mapper.getMappedFields();

        Map<String, Object>[] valueMaps = new HashMap[entityList.size()];

        for (int i = 0; i < entityList.size(); i++) {

            Entity e = entityList.get(i);

            Map<String, Object> valueMap = new HashMap<String, Object>();

            for (String key : keys) {
                try {
                    
                    PropertyDescriptor pd = mappedFields.get(key.toLowerCase());
                    Method getMethod = pd.getReadMethod();

                    if (getMethod != null) {
                        Object obj = getMethod.invoke(e);
                        if (obj == null) {
                            continue;
                        }
                        valueMap.put(key, obj);
                    }

                }
                catch (Exception ex) {
                    ex.printStackTrace();
                    continue;
                }
            }

            valueMaps[i] = valueMap;
        }

        return new JdbcSqlContext(batchUpdateSql, valueMaps);
    }

    /**
     * 
     * @title: buildDeleteSQL
     * @author leise
     * @description:
     * @date 2016年4月14日 上午11:29:31
     * @throws
     */
    public static JdbcSqlContext buildDeleteSQL(Entity entity) {

        Class<? extends Entity> clazz = entity.getClass();
        EntityRegister entityRegister = EntityCacheRegister.get(clazz.getName());
        String tableName = entityRegister.getTableName();
        Map<String, String> pks = entityRegister.getPks();

        StringBuffer sqlBuffer = new StringBuffer();
        StringBuffer argsBuffer = new StringBuffer();

        sqlBuffer.append("delete from ");
        sqlBuffer.append(tableName);
        sqlBuffer.append(" where 1=1");

        BeanPropertyColumnMapper<? extends Entity> mapper = entityRegister.getBeanPropertyColumnMapper();
        Map<String, PropertyDescriptor> mappedFields = mapper.getMappedFields();
        Map<String, Object> valueMap = new HashMap<String, Object>();

        Set<String> keySet = pks.keySet();
        for (String key : keySet) {
            try {
                PropertyDescriptor pd = mappedFields.get(key.toLowerCase());
                Method getMethod = pd.getReadMethod();
                if (getMethod != null) {
                    Object obj = getMethod.invoke(entity);
                    if (obj != null) {
                        valueMap.put(key, obj);
                        String columnName = pks.get(key);
                        argsBuffer.append(" and ");
                        argsBuffer.append(columnName);
                        argsBuffer.append("=:");
                        argsBuffer.append(key);
                    }
                }
            }
            catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                continue;
            }
        }
        sqlBuffer.append(argsBuffer);

        Map<String, Object>[] valueMaps = new HashMap[1];
        valueMaps[0] = valueMap;

        return new JdbcSqlContext(sqlBuffer.toString(), valueMaps);
    }

    /**
     * 
     * @title: buildDeleteSQL
     * @author leise
     * @description:
     * @date 2016年4月14日 上午11:29:31
     * @throws
     */
    public static JdbcSqlContext buildBatchDeleteSQL(List<? extends Entity> entityList) {

        Entity entity = entityList.get(0);
        JdbcSqlContext sqlContext = buildDeleteSQL(entity);
        String batchDeleteSql = sqlContext.getSql();

        Class<? extends Entity> clazz = entity.getClass();
        String classFullName = clazz.getName();
        EntityRegister entityRegister = EntityCacheRegister.get(classFullName);
        Map<String, String> pks = entityRegister.getPks();

        BeanPropertyColumnMapper<? extends Entity> mapper = entityRegister.getBeanPropertyColumnMapper();
        Map<String, PropertyDescriptor> mappedFields = mapper.getMappedFields();

        Map<String, Object>[] valueMaps = new HashMap[entityList.size()];

        for (int i = 0; i < entityList.size(); i++) {

            Map<String, Object> valueMap = new HashMap<String, Object>();

            Set<String> keySet = pks.keySet();
            for (String key : keySet) {
                try {
                    PropertyDescriptor pd = mappedFields.get(key.toLowerCase());
                    Method getMethod = pd.getReadMethod();
                    if (getMethod != null) {
                        Object obj = getMethod.invoke(entity);
                        if (obj != null) {
                            valueMap.put(key, obj);
                        }
                    }
                }
                catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    continue;
                }
            }

            valueMaps[i] = valueMap;
        }

        return new JdbcSqlContext(batchDeleteSql, valueMaps);
    }

}
