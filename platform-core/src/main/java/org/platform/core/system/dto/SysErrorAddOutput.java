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

import org.platform.core.base.dto.OutputHeader;
import org.platform.core.system.entity.TSysError;

/**
 * @classname: QuartzJobAddInput
 * @description: TODO(这里用一句话描述这个类的作用)
 */

public class SysErrorAddOutput extends OutputHeader {

    /**
     * @fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
     */
    private static final long serialVersionUID = 5620379041168673767L;

    private List<TSysError> failedErrorList;

    /**
     * @return the failedErrorList
     */

    public List<TSysError> getFailedErrorList() {
        return failedErrorList;
    }

    /**
     * @param failedErrorList the failedErrorList to set
     */

    public void setFailedErrorList(List<TSysError> failedErrorList) {
        this.failedErrorList = failedErrorList;
    }

}
