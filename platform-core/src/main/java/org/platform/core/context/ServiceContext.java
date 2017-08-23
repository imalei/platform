/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: ServiceContext.java
 * @package org.platform.core.base.context
 * @author leise
 * @date 2016年4月7日 下午10:13:14
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.context;

import java.util.HashMap;
import java.util.Map;

/**
 * @classname: ServiceContext
 * @description: TODO(这里用一句话描述这个类的作用)
 */

public class ServiceContext {

    private String name;

    private String declareClass;

    private Map<String, Object> context;

    public ServiceContext() {
        context = new HashMap<String, Object>();
    }

    public ServiceContext(String name) {
        this.setName(name);
        this.setDeclareClass(name);
        context = new HashMap<String, Object>();
    }

    public void put(String key, Object value) {
        context.put(key, value);
    }

    public Object get(String key) {
        return context.get(key);
    }

    public void remove(String key) {
        context.remove(key);
    }

    public boolean contains(String key) {
        return context.containsKey(key);
    }

    public void clear() {
        context.clear();
    }

    /**
     * @return the name
     */

    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the declareClass
     */

    public String getDeclareClass() {
        return declareClass;
    }

    /**
     * @param declareClass the declareClass to set
     */

    public void setDeclareClass(String declareClass) {
        this.declareClass = declareClass;
    }

}
