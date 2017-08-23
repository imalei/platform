/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: Cacheable.java
 * @package org.platform.core.cache.annotation
 * @author leise
 * @date 2016年8月4日 下午9:29:45
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.cache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @classname: Cacheable
 * @description: TODO(这里用一句话描述这个类的作用)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface CacheEvict {

    public String cacheName() default "default";

    public String key() default "";

    public int expire() default 0;

    String cacheManager() default "";

}
