/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: BeanUtils.java
 * @package org.platform.core.utils
 * @author leise
 * @date 2016年8月3日 下午12:53:58
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @classname: BeanUtils
 * @description: TODO(这里用一句话描述这个类的作用)
 */

@Component
public class BeanUtils implements ApplicationContextAware {


    private ApplicationContext applicationContext;
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;

    }
    
    public <T> T getBean(String beanName, Class<T> type) {
        return BeanFactoryAnnotationUtils.qualifiedBeanOfType(this.applicationContext, type, beanName);
    }
    
}
