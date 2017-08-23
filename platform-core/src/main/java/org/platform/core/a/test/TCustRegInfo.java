/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: CustInfo.java
 * @package org.p2p.service.entity
 * @author leise
 * @date 2016年3月25日 下午4:18:28
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.a.test;

import org.platform.core.annotation.entity.PK;
import org.platform.core.annotation.entity.Table;
import org.platform.core.base.entity.BaseEntity;

/**
 * @classname: CustInfo
 * @description: TODO(这里用一句话描述这个类的作用)
 */
@Table("T_CUST_REGINFO")
public class TCustRegInfo extends BaseEntity {

    /**
     * @fields serialVersionUID : 串行号
     */
    private static final long serialVersionUID = 2641558062564585656L;

    /** 客户编号 */
    @PK
    private String custNo;

    /** 客户类型 */
    private String custType;

    /** 客户昵称 */
    private String custAlias;

    /** 手机号码 */
    private String custMobile;

    /** 客户邮箱 */
    private String custEmail;

    /** 登录密码 */
    private String loginPassword;

    /** 客户姓名 */
    private String custName;

    /** 证件类型 */
    private String custCardtype;

    /** 证件号码 */
    private String custCardno;

    /** 实名认证状态 */
    private String authState;

    /**
     * @return the custNo
     */

    public String getCustNo() {
        return custNo;
    }

    /**
     * @param custNo the custNo to set
     */

    public void setCustNo(String custNo) {
        this.custNo = custNo;
    }

    /**
     * @return the custType
     */

    public String getCustType() {
        return custType;
    }

    /**
     * @param custType the custType to set
     */

    public void setCustType(String custType) {
        this.custType = custType;
    }

    /**
     * @return the custAlias
     */

    public String getCustAlias() {
        return custAlias;
    }

    /**
     * @param custAlias the custAlias to set
     */

    public void setCustAlias(String custAlias) {
        this.custAlias = custAlias;
    }

    /**
     * @return the custEmail
     */

    public String getCustEmail() {
        return custEmail;
    }

    /**
     * @param custEmail the custEmail to set
     */

    public void setCustEmail(String custEmail) {
        this.custEmail = custEmail;
    }

    /**
     * @return the loginPassword
     */

    public String getLoginPassword() {
        return loginPassword;
    }

    /**
     * @param loginPassword the loginPassword to set
     */

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    /**
     * @return the custName
     */

    public String getCustName() {
        return custName;
    }

    /**
     * @param custName the custName to set
     */

    public void setCustName(String custName) {
        this.custName = custName;
    }

    /**
     * @return the authState
     */

    public String getAuthState() {
        return authState;
    }

    /**
     * @param authState the authState to set
     */

    public void setAuthState(String authState) {
        this.authState = authState;
    }

    /**
     * @return the custMobile
     */

    public String getCustMobile() {
        return custMobile;
    }

    /**
     * @param custMobile the custMobile to set
     */

    public void setCustMobile(String custMobile) {
        this.custMobile = custMobile;
    }

    /**
     * @return the custCardtype
     */

    public String getCustCardtype() {
        return custCardtype;
    }

    /**
     * @param custCardtype the custCardtype to set
     */

    public void setCustCardtype(String custCardtype) {
        this.custCardtype = custCardtype;
    }

    /**
     * @return the custCardno
     */

    public String getCustCardno() {
        return custCardno;
    }

    /**
     * @param custCardno the custCardno to set
     */

    public void setCustCardno(String custCardno) {
        this.custCardno = custCardno;
    }

}
