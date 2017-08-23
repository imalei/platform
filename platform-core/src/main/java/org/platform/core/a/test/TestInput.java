/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: TestInput.java
 * @package org.platform.core.a.test
 * @author leise
 * @date 2016年4月27日 下午7:26:16
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.a.test;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.platform.core.base.dto.InputHeader;

/**
 * @classname: TestInput
 * @description: TODO(这里用一句话描述这个类的作用)
 */

@XmlRootElement(name = "root")
@XmlAccessorType(XmlAccessType.FIELD)
public class TestInput extends InputHeader {

    /**
     * @fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
     */

    private static final long serialVersionUID = 3334386805143084405L;

    @XmlElement
    private String custNo;

    /** 客户类型 */
    @XmlElement
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

}
