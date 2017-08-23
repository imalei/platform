/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: ActionAnnotationUtils.java
 * @package org.platform.core.utils
 * @author leise
 * @date 2016年7月19日 下午6:24:28
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.utils;

import java.lang.annotation.Annotation;

import org.platform.core.cache.register.ActionConfigCacheRegister;
import org.platform.core.initializer.register.ActionConfigRegister;
import org.springframework.util.Assert;

/**
 * @classname: ActionAnnotationUtils
 * @description: TODO(这里用一句话描述这个类的作用)
 */

public class ActionAnnotationUtils {

    @SuppressWarnings("unchecked")
    public static <T extends Annotation> T getAnnotation(String declareClassName, String actionId,
            Class<? extends Annotation> annotationType) {

        ActionConfigRegister register = ActionConfigCacheRegister.get(declareClassName);
        Assert.notNull(register, "the action config register must not be null");
        T t = (T) register.getActionAnnotation(actionId, annotationType);
        return t;
    }

}
