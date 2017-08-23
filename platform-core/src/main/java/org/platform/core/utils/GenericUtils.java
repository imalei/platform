/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: GenericUtils.java
 * @package org.platform.core.utils
 * @author leise
 * @date 2015-8-29 下午7:16:35
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */
package org.platform.core.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 泛型参数工具，用于获取某个泛型类实际的泛型参数的类型
 * 
 */
public class GenericUtils {

    /**
     * 
     * @title: getGenericTypeFromSuperclass
     * @author
     * @description: 获取指定class的泛型参数的类型
     * @date 2015-8-29 下午7:16:35
     * @param clazz
     * @param parameterIndex
     * @return
     * @throws
     */
    @SuppressWarnings("rawtypes")
    public static final Class getGenericTypeFromSuperclass(Class clazz, int parameterIndex) {
        return getGenericType(parameterIndex, clazz.getGenericSuperclass());
    }

    /**
     * 
     * @title: getGenericType
     * @author
     * @description: 获取指定Type的泛型参数的类型
     * @date 2015-8-29 下午7:16:50
     * @param parameterIndex
     * @param genType
     * @return
     * @throws
     */
    @SuppressWarnings("rawtypes")
    private static final Class getGenericType(int parameterIndex, Type genType) {
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (parameterIndex >= params.length || parameterIndex < 0) {
            throw new RuntimeException("Index outof bounds");
        }
        if (!(params[parameterIndex] instanceof Class)) {
            return Object.class;
        }
        return (Class) params[parameterIndex];
    }

}
