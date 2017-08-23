/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: BeanValidator.java
 * @package org.platform.core.validator
 * @author leise
 * @date 2016年5月12日 下午5:40:27
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.platform.core.error.enums.ErrorCode;
import org.platform.core.error.exception.ExceptionBuilder;
import org.platform.core.error.exception.PFException;

/**
 * @classname: BeanValidator
 * @description: TODO(这里用一句话描述这个类的作用)
 */

public class BeanValidator {

    /**
     * 调用JSR303的validate方法, 验证失败时抛出PlatformException,
     */
    public static void validateWithException(Validator validator, Object object, Class<?>... groups) throws PFException {
        Set<? extends ConstraintViolation<?>> constraintViolations = validator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            List<String> errorMsgList = new ArrayList<String>();
            for (ConstraintViolation<?> violation : constraintViolations) {
                errorMsgList.add(violation.getMessage());
            }
            throw ExceptionBuilder.newException().withError(ErrorCode.VALIDATE_DATA_FAILED)
                    .withMessageArgs(new Object[] { errorMsgList.toString() }).build();
        }
    }

}
