/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: Service.java
 * @package org.platform.core.annotation.service
 * @author leise
 * @date 2016年5月12日 下午6:23:08
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.annotation.service;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

/**
 * @classname: Service
 * @description: TODO(这里用一句话描述这个类的作用)
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface PFService {
    
    String value() default "";

    String serviceId();

    String serviceVersion();

    String serviceDesc();

    boolean serviceEnable() default true;

}
