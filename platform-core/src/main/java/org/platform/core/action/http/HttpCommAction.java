/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: ReadFileDataAction.java
 * @package org.platform.core.action.file
 * @author leise
 * @date 2016年5月30日 下午12:21:38
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.action.http;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.platform.core.action.Action;
import org.platform.core.annotation.action.HttpAction;
import org.platform.core.base.dto.RequestObject;
import org.platform.core.base.dto.ResponseObject;
import org.platform.core.comm.http.resource.HttpResource;
import org.platform.core.error.enums.ErrorCode;
import org.platform.core.error.exception.ExceptionBuilder;
import org.platform.core.error.exception.PFException;
import org.platform.core.formatter.Formatter;
import org.platform.core.utils.ActionAnnotationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * @classname: ReadFileDataAction
 * @description: TODO(这里用一句话描述这个类的作用)
 */

@Component
public class HttpCommAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(HttpCommAction.class);

    @Autowired
    private CloseableHttpClient httpClient;

    @Autowired
    private List<? extends HttpResource> httpResources;

    @Autowired
    private List<? extends Formatter> formatters;

    public ResponseObject execute(String actionId, String declareClassName, RequestObject reqObj) throws PFException {

        logger.info("[执行action开始]--[actionId:{}]--[调用方:{}]------", actionId, declareClassName);

        Assert.notNull(reqObj, "the reqObj is required");

        HttpAction httpAction = ActionAnnotationUtils.getAnnotation(declareClassName, actionId, HttpAction.class);
        Assert.notNull(httpAction, "the HttpAction config must be not null");

        String actionName = httpAction.name();
        logger.info("[actionId:{}]--[调用方:{}]--[actionName:{}]", actionId, declareClassName, actionName);

        String serviceId = httpAction.serviceId();
        Assert.isTrue(serviceId.equals(reqObj.getServiceId()), "the serviceId must sets right value");

        Class<? extends HttpResource> httpResourceClazz = httpAction.httpResource();
        HttpResource httpResource = null;
        for (HttpResource res : httpResources) {
            if (httpResourceClazz.getName().equals(res.getClass().getName())) {
                httpResource = res;
                break;
            }
        }
        Assert.notNull(httpResource, "the http resource is required");

        Class<? extends Formatter> formatterClazz = httpAction.formatter();
        Formatter formatter = null;
        for (Formatter fmt : formatters) {
            if (formatterClazz.getName().equals(fmt.getClass().getName())) {
                formatter = fmt;
                break;
            }
        }
        Assert.notNull(formatter, "the formatter is required");

        Class<?> resClass = httpAction.resClass();
        Assert.notNull(resClass, "the response class is required");

        String format = httpAction.format();
        Assert.isTrue(StringUtils.isNotBlank(format), "http format is required");

        HttpRequestBase httpRequest = null;

        try {
            httpRequest = buildHttpRequest(httpResource, httpAction);
            configHttpRequest(httpRequest, httpResource, httpAction);
            String defaultCharset = httpResource.getCharset();
            String sendMessage = formatRequestObject(httpRequest, reqObj, format, formatter);
            Assert.isTrue(StringUtils.isNotBlank(sendMessage), "the sendMessage must be not blank");
            logger.info("[actionId:{}--[调用方:{}]--[http request sendMessage:{}]", actionId, declareClassName,
                    sendMessage);

            if (httpRequest instanceof HttpPost) {
                ((HttpPost) httpRequest).setEntity(new StringEntity(sendMessage, defaultCharset));
            }
        }
        catch (Exception e) {
            throw ExceptionBuilder.newException().withError(ErrorCode.ACTION_EXECUTE_FAILD)
                    .withMessageArgs(new Object[] { "HTTP通讯" }).withCause(e).build();
        }

        CloseableHttpResponse response = null;
        ResponseObject respObj = null;

        try {
            try {
                response = httpClient.execute(httpRequest, HttpClientContext.create());
            }
            catch (HttpHostConnectException e) {
                throw ExceptionBuilder.newException().withError(ErrorCode.HTTP_COMM_FAILED).withCause(e).build();
            }

            String receiveMessage = EntityUtils.toString(response.getEntity());
            logger.info("[actionId:{}]--[调用方:{}]--[http response receiveMessage:{}]", actionId, declareClassName,
                    receiveMessage);

            Integer statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != null && statusCode != 200) {
                String errorMessage = "http request failed! statusCode = " + statusCode + "; msg="
                        + response.getStatusLine().getReasonPhrase();
                throw ExceptionBuilder.newException().withError(ErrorCode.HTTP_COMM_FAILED).withMessage(errorMessage)
                        .build();
            }

            respObj = formatResponseObject(receiveMessage, resClass, formatter);
            logger.info("[actionId:{}]--[调用方:{}]--[http response object:{}]", actionId, declareClassName, respObj);
        }
        catch (Exception e) {
            throw ExceptionBuilder.newException().withError(ErrorCode.HTTP_COMM_UNKNOW).withCause(e).build();
        }
        finally {
            HttpClientUtils.closeQuietly(response);
        }

        logger.info("[执行action结束]--[actionId:{}]--[调用方:{}]------", actionId, declareClassName);

        return respObj;

    }

    public ResponseObject formatResponseObject(String receiveMessage, Class<?> resClass, Formatter formatter)
            throws Exception {
        ResponseObject respObj = (ResponseObject) formatter.unformat(receiveMessage, resClass);

        return respObj;
    }

    public String formatRequestObject(HttpRequestBase httpRequest, RequestObject reqObj, String format,
            Formatter formatter) throws Exception {

        String sendMessage = formatter.format(reqObj);

        if ("json".equalsIgnoreCase(format)) {
            httpRequest.setHeader(new BasicHeader("Accept", "application/json"));
            httpRequest.setHeader(new BasicHeader("content-Type", "application/json"));

        } else if ("xml".equalsIgnoreCase(format)) {
            httpRequest.setHeader(new BasicHeader("Accept", "application/xml"));
            httpRequest.setHeader(new BasicHeader("content-Type", "application/xml"));
        }

        return sendMessage;
    }

    public HttpRequestBase buildHttpRequest(HttpResource httpResource, HttpAction httpAction) {

        HttpRequestBase httpRequest = null;

        String defaultUrl = httpResource.getUrl();
        String defaultMethod = httpResource.getMethod();

        String method = StringUtils.isNotBlank(httpAction.method()) ? httpAction.method() : defaultMethod;

        String path = httpAction.path();
        String sendUrl = new StringBuffer().append(defaultUrl).append(path).toString();

        if (method.equalsIgnoreCase("get")) {
            httpRequest = new HttpGet(sendUrl);
        } else {
            httpRequest = new HttpPost(sendUrl);
        }

        return httpRequest;

    }

    public void configHttpRequest(HttpRequestBase httpRequest, HttpResource httpResource, HttpAction httpAction) {
        Integer connectTimeout = httpResource.getConnectTimeout();
        Integer connectionRequestTimeout = httpResource.getConnectionRequestTimeout();
        Integer socketTimeout = httpAction.socketTimeout() == -2 ? httpResource.getSocketTimeout() : httpAction
                .socketTimeout();
        RequestConfig config = RequestConfig.custom().setConnectTimeout(connectTimeout)
                .setConnectionRequestTimeout(connectionRequestTimeout).setSocketTimeout(socketTimeout).build();
        httpRequest.setConfig(config);

    }

}
