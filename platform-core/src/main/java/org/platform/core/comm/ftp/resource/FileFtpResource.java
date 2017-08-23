/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: PayHttpResource
 * @package org.platform.core.http
 * @author leise
 * @date 2016年6月17日 下午6:13:44
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.comm.ftp.resource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @classname: PayHttpResource
 * @description: TODO(这里用一句话描述这个类的作用)
 */
@ConfigurationProperties(prefix = FileFtpResource.PREFIX)
@Component
public class FileFtpResource implements FtpResource {

    public static final String PREFIX = "file.ftp.resource";

    private String url;

    private Integer port;

    private String user;

    private String password;

    private String remotePath;

    private String localPath;

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
     * @return the port
     */

    public Integer getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */

    public void setPort(Integer port) {
        this.port = port;
    }

    /**
     * @return the user
     */

    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */

    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the password
     */

    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the remotePath
     */

    public String getRemotePath() {
        return remotePath;
    }

    /**
     * @param remotePath the remotePath to set
     */

    public void setRemotePath(String remotePath) {
        this.remotePath = remotePath;
    }

    /**
     * @return the localPath
     */

    public String getLocalPath() {
        return localPath;
    }

    /**
     * @param localPath the localPath to set
     */

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

}
