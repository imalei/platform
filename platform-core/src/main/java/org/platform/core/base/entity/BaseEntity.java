/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: BaseEntity.java
 * @package org.platform.core.base.entity
 * @author leise
 * @date 2016年5月11日 下午7:16:45
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.base.entity;

import java.sql.Timestamp;

/**
 * @classname: BaseEntity
 * @description: TODO(这里用一句话描述这个类的作用)
 */

public class BaseEntity implements Entity {

    /**
     * @fields serialVersionUID : 串行号
     */

    private static final long serialVersionUID = -7997302406957886765L;

    /** 创建日期 **/
    private Timestamp createTime;

    /** 修改日期 **/
    private Timestamp modifyTime;

    /**
     * @return the createTime
     */

    public Timestamp getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime the createTime to set
     */

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    /**
     * @return the modifyTime
     */

    public Timestamp getModifyTime() {
        return modifyTime;
    }

    /**
     * @param modifyTime the modifyTime to set
     */

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

}
