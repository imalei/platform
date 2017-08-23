/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: Formatter.java
 * @package org.platform.core.formatter
 * @author leise
 * @date 2016年8月3日 上午11:57:20
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.formatter;

/**
 * @classname: Formatter
 * @description: TODO(这里用一句话描述这个类的作用)
 */

public interface Formatter {

    public <T> String format(T t) throws Exception;

    public <T> T unformat(String msg, Class<T> type) throws Exception;
}
