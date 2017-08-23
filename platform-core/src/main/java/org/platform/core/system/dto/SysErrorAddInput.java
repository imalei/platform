/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: QuartzJobAddInput.java
 * @package org.platform.core.system.dto
 * @author leise
 * @date 2016年7月7日 下午8:40:29
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.system.dto;

import java.util.List;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.platform.core.base.dto.InputHeader;
import org.platform.core.system.entity.TSysError;

/**
 * @classname: QuartzJobAddInput
 * @description: TODO(这里用一句话描述这个类的作用)
 */

public class SysErrorAddInput extends InputHeader {

    /**
     * @fields serialVersionUID : 串行号
     */
    private static final long serialVersionUID = 6887721825795663722L;

    /** 批量添加，批量添加并更新 */
    @Pattern(regexp = "^(1|2)$", message = "the field [flag] value must be '1' or '2'")
    @NotBlank(message = "the field [flag] is required and the value must be '1' or '2' ")
    private String flag;

    @NotEmpty(message = "the field [errorList] is required")
    private List<TSysError> errorList;

    /**
     * @return the flag
     */

    public String getFlag() {
        return flag;
    }

    /**
     * @param flag the flag to set
     */

    public void setFlag(String flag) {
        this.flag = flag;
    }

    /**
     * @return the errorList
     */

    public List<TSysError> getErrorList() {
        return errorList;
    }

    /**
     * @param errorList the errorList to set
     */

    public void setErrorList(List<TSysError> errorList) {
        this.errorList = errorList;
    }

}
