/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: PayHttpResource
 * @package org.platform.core.comm.http.resource
 * @author leise
 * @date 2016年6月17日 下午6:13:44
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.comm.http.resource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @classname: PayHttpResource
 * @description: TODO(这里用一句话描述这个类的作用)
 */
@Component
@ConfigurationProperties(prefix = PayHttpResource.PREFIX)
public class PayHttpResource implements HttpResource {

    public static final String PREFIX = "pay.http.resource";

    private String url;

    private String method;

    private String charset;

    private Integer connectTimeout;

    private Integer connectionRequestTimeout;

    private Integer socketTimeout;

    /**
     * @return the url
     */

    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the method
     */

    public String getMethod() {
        return method;
    }

    /**
     * @param method the method to set
     */

    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * @return the charset
     */

    public String getCharset() {
        return charset;
    }

    /**
     * @param charset the charset to set
     */

    public void setCharset(String charset) {
        this.charset = charset;
    }

    /**
     * @return the connectTimeout
     */

    public Integer getConnectTimeout() {
        return connectTimeout;
    }

    /**
     * @param connectTimeout the connectTimeout to set
     */

    public void setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    /**
     * @return the connectionRequestTimeout
     */

    public Integer getConnectionRequestTimeout() {
        return connectionRequestTimeout;
    }

    /**
     * @param connectionRequestTimeout the connectionRequestTimeout to set
     */

    public void setConnectionRequestTimeout(Integer connectionRequestTimeout) {
        this.connectionRequestTimeout = connectionRequestTimeout;
    }

    /**
     * @return the socketTimeout
     */

    public Integer getSocketTimeout() {
        return socketTimeout;
    }

    /**
     * @param socketTimeout the socketTimeout to set
     */

    public void setSocketTimeout(Integer socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

}
