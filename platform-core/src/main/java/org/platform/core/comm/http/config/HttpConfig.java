/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: HttpClientConfig.java
 * @package org.platform.core.comm.http.config
 * @author leise
 * @date 2016年6月8日 下午7:09:23
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.comm.http.config;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @classname: HttpClientConfig
 * @description: TODO(这里用一句话描述这个类的作用)
 */

@Configuration
public class HttpConfig {

    @Value("${platform.http.pool.max-total:200}")
    private Integer maxTotal;

    @Value("${platform.http.pool.default-max-per-route:50}")
    private Integer defaultMaxPerRoute;

    private static PoolingHttpClientConnectionManager pccm = new PoolingHttpClientConnectionManager();

    @Bean
    public CloseableHttpClient httpClient() {

        // 连接池最大并发连接数
        pccm.setMaxTotal(maxTotal);
        // 单路由最大并发数
        pccm.setDefaultMaxPerRoute(defaultMaxPerRoute);

        HttpRequestRetryHandler httpRequestRetryHandler = new DefaultHttpRequestRetryHandler(0, false);

        CloseableHttpClient httpclient = HttpClients.custom()
                .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy() {

                    @Override
                    public long getKeepAliveDuration(final HttpResponse response, final HttpContext context) {
                        long keepAlive = super.getKeepAliveDuration(response, context);
                        if (keepAlive == -1) {
                            keepAlive = 10000;
                        }
                        return keepAlive;
                    }
                }).setRetryHandler(httpRequestRetryHandler).setConnectionManager(pccm).build();
        return httpclient;

    }

}
