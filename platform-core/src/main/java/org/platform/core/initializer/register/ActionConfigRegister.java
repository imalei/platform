/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: ActionProcessRegister.java
 * @package org.platform.core.initialize.bean
 * @author leise
 * @date 2016年6月1日 下午4:13:09
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.initializer.register;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;
import org.platform.core.action.ActionConfigurable;
import org.platform.core.error.exception.ExceptionBuilder;
import org.platform.core.error.exception.PFException;
import org.springframework.util.Assert;

/**
 * @classname: ActionProcessRegister
 * @description: TODO(这里用一句话描述这个类的作用)
 */

public class ActionConfigRegister {

    private Map<String, Annotation> actionAnnotationMap;

    @SuppressWarnings("unchecked")
    public ActionConfigRegister(String actionConfigClassName) {

        try {
            actionAnnotationMap = new HashMap<String, Annotation>();
            Class<? extends ActionConfigurable> actionConfigClass = ClassUtils.getClass(actionConfigClassName);

            init(actionConfigClass);

        }
        catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * @title: init
     * @author leise
     * @description:
     * @date 2016年7月18日 下午6:41:48
     * @throws
     */

    private void init(Class<? extends ActionConfigurable> actionConfigClass) throws PFException {

        Field[] fields = actionConfigClass.getDeclaredFields();

        try {
            for (Field field : fields) {

                String actionId = null;

                if (Modifier.isStatic(field.getModifiers())) {
                    field.setAccessible(true);
                    actionId = (String) field.get(null);
                    Assert.isTrue(StringUtils.isNotBlank(actionId), "the field[" + field.getName()
                            + "] must not be blank, the declareClass is" + actionConfigClass.getName());
                    System.out.println("-------------已被初始化的action config-------" + actionConfigClass.getName() +"---------" + field.getName());
                } else {
                    System.out.println("-------------未被初始化的action config-------" + actionConfigClass.getName() +"---------" + field.getName());
                    continue;
                }

                Annotation[] annotations = field.getDeclaredAnnotations();
                for (Annotation annotation : annotations) {
                    if (ActionProcessRegister.PROCESS_ACTION_ANNOTATIONS.contains(annotation.annotationType())) {
                        this.putActionAnnotation(actionId, annotation);
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            // TODO
            throw ExceptionBuilder.newException().withCause(e).withMessage(e.getMessage()).build();
        }
    }

    /**
     * 
     * @title: putActionAnnotation
     * @author leise
     * @description:
     * @date 2016年6月15日 下午5:14:32
     * @throws
     */
    public void putActionAnnotation(String actionId, Annotation annotation) {
        String key = StringUtils.join(new Object[] { actionId, annotation.annotationType().getName() }, "#");
        actionAnnotationMap.put(key, annotation);
    }

    /**
     * 
     * @title: getActionAnnotation
     * @author leise
     * @description:
     * @date 2016年6月15日 下午5:14:37
     * @throws
     */
    public Annotation getActionAnnotation(String actionId, Class<? extends Annotation> annotationType) {
        String key = StringUtils.join(new Object[] { actionId, annotationType.getName() }, "#");
        Annotation annotation = actionAnnotationMap.get(key);
        return annotation;
    }

}
