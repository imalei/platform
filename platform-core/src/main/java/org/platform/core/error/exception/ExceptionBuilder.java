/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: PFExceptionBuilder.java
 * @package org.platform.core.error.exception
 * @author leise
 * @date 2016年7月11日 下午12:23:01
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.error.exception;

import java.text.MessageFormat;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.platform.core.constant.Constant;
import org.platform.core.error.enums.ErrorCode;
import org.platform.core.system.entity.TSysError;

/**
 * @classname: PFExceptionBuilder
 * @description: TODO(这里用一句话描述这个类的作用)
 */

public class ExceptionBuilder {

    private Throwable cause;

    private ErrorCode error;

    private Object[] messageArgs;

    private String message;

    protected ExceptionBuilder() {

    }

    public static ExceptionBuilder newException() {
        return new ExceptionBuilder();
    }

    public ExceptionBuilder withCause(Throwable cause) {
        this.cause = cause;
        return this;
    }

    public ExceptionBuilder withError(ErrorCode error) {
        this.error = error;
        return this;
    }

    public ExceptionBuilder withMessage(String message) {
        this.message = message;
        return this;
    }

    public ExceptionBuilder withMessageArgs(Object[] messageArgs) {
        this.messageArgs = messageArgs;
        return this;
    }

    public PFException build() {

        if (cause != null) {
            if (cause instanceof PFException) {
                return (PFException) cause;
            }

            if (cause instanceof IllegalArgumentException) {
                IllegalArgumentException e = (IllegalArgumentException) cause;
                this.message = e.getMessage();
            }
        }

        if (this.error == null) {
            this.error = ErrorCode.DEFAULT_FAILED;
        }

        String errorNo = this.error.getErrorNo();
        String errorCode = null;
        String errorMessage = null;
        TSysError sysError = Constant.SYS_ERROR_MAP.get(errorNo);
        if (sysError == null) {
            errorCode = errorNo;
        } else {
            errorCode = sysError.getErrorCode();
        }

        if (StringUtils.isNotBlank(this.message)) {
            errorMessage = this.message;
        } else {
            if (sysError != null) {
                errorMessage = sysError.getErrorMessage();
            }
        }
        if (StringUtils.isNotBlank(errorMessage) && ArrayUtils.isNotEmpty(messageArgs)) {
            errorMessage = MessageFormat.format(errorMessage, messageArgs);
        }

        if (errorMessage == null) {
            errorMessage = "undefined error message";
        }

        PFException ex = new PFException(errorCode, errorMessage, cause);

        return ex;
    }

}
