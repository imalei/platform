/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: MapperUtils.java
 * @package org.platform.core.utils
 * @author leise
 * @date 2016年5月5日 下午12:44:28
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @classname: MapperUtils
 * @description: TODO(这里用一句话描述这个类的作用)
 */

public class MapperUtils {

    public static Object covertToDto(Map<String, Object> resultMap, Class<?> dtoClass) throws Exception {

        Object obj = dtoClass.newInstance();
        Field[] fields = dtoClass.getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            if (resultMap.containsKey(fieldName)) {
                Object value = resultMap.get(fieldName);
                PropertyDescriptor pd = new PropertyDescriptor(fieldName, dtoClass);
                Method setMethod = pd.getWriteMethod();
                setMethod.invoke(obj, value);
            }
        }

        return obj;
    }

}
