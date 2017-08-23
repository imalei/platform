/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: PFException.java
 * @package org.platform.core.exception
 * @author leise
 * @date 2016年3月31日 下午5:20:23
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.error.exception;

/**
 * @classname: PFException
 * @description: TODO(这里用一句话描述这个类的作用)
 */

public class PFException extends RuntimeException {

    /**
     * @fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
     */

    private static final long serialVersionUID = 371399207428774494L;

    /** 错误代码 **/
    private String errorCode = "999999";

    private Throwable cause;

    public PFException(final String errorCode, final String errorMessage, final Throwable cause) {
        super(errorMessage, cause);
        this.errorCode = errorCode;
        this.cause = cause;
    }

    /**
     * @return the errorCode
     */

    public String getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode the errorCode to set
     */

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * @return the errorMessage
     */

    public String getErrorMessage() {
        String errorMessage = super.getMessage();
        return errorMessage;
    }

    public String getDetailErrorMessage() {

        String errorMessage = super.getMessage();
        String causeMsg = null;
        if (this.cause != null) {
            causeMsg = this.cause.getMessage();
        }

        return errorMessage + " caused by: " + causeMsg;
    }

    /**
     * @return the cause
     */

    public Throwable getCause() {
        return cause;
    }

    /**
     * @param cause the cause to set
     */

    public void setCause(Throwable cause) {
        this.cause = cause;
    }

}
