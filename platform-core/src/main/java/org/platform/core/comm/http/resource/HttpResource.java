/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: HttpResource.java
 * @package org.platform.core.commu.http
 * @author leise
 * @date 2016年6月17日 下午6:12:46
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.comm.http.resource;

/**
 * @classname: HttpResource
 * @description: TODO(这里用一句话描述这个类的作用)
 */
public interface HttpResource {

    public String getUrl();

    public String getMethod();

    public String getCharset();

    public Integer getConnectTimeout();

    public Integer getConnectionRequestTimeout();

    public Integer getSocketTimeout();

}
