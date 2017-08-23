/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: JosnFormatter.java
 * @package org.platform.core.formatter
 * @author leise
 * @date 2016年8月3日 上午11:57:43
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.formatter.json;

import org.platform.core.formatter.Formatter;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

/**
 * @classname: JosnFormatter
 * @description: TODO(这里用一句话描述这个类的作用)
 */

@Component
public class JSONFormatter implements Formatter {

    @Override
    public <T> String format(T t) {
        String result = JSON.toJSONString(t);
        return result;
    }

    @Override
    public <T> T unformat(String msg, Class<T> type) {
        T result = (T) JSON.parseObject(msg, type);
        return result;
    }

}
