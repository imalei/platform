/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: TSysError.java
 * @package org.platform.core.system.entity
 * @author leise
 * @date 2016年7月8日 下午6:54:09
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.system.entity;

import org.platform.core.annotation.entity.PK;
import org.platform.core.annotation.entity.Table;
import org.platform.core.base.entity.BaseEntity;

/**
 * @classname: TSysError
 * @description: TODO(这里用一句话描述这个类的作用)
 */

@Table("T_SYS_ERROR")
public class TSysError extends BaseEntity {

    /**
     * @fields serialVersionUID : 串行号
     */
    private static final long serialVersionUID = -1483445165597395848L;

    /** 错误编号 */
    @PK
    private String errorNo;

    /** 错误代码 */
    private String errorCode;

    /** 错误信息 */
    private String errorMessage;

    /**
     * @return the errorNo
     */

    public String getErrorNo() {
        return errorNo;
    }

    /**
     * @param errorNo the errorNo to set
     */

    public void setErrorNo(String errorNo) {
        this.errorNo = errorNo;
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
        return errorMessage;
    }

    /**
     * @param errorMessage the errorMessage to set
     */

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
