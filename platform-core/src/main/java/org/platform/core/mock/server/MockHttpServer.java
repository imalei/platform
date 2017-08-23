/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: HttpMockServer.java
 * @package org.platform.core.mock.server
 * @author leise
 * @date 2016年6月12日 下午5:26:52
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.mock.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.BindException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

/**
 * @classname: MockHttpServer
 * @description: TODO(这里用一句话描述这个类的作用)
 */

@Component
//@ActiveProfiles({ "dev", "junit" })
public class MockHttpServer implements InitializingBean {

    @Value("${platform.mock.server.enable:false}")
    private boolean mockServerEnbale;

    private final String HOST = "127.0.0.1";

    private final int PORT = 9999;

    private final String CONTEXT = "/mockHttpServer";

    private final String ENCODING = "utf-8";

    private final String XML_FORMAT = "xml";

    private final String JSON_FORMAT = "json";

    private final static Logger logger = LoggerFactory.getLogger(MockHttpServer.class);

    /**
     * 创建一个简单的http server
     * 
     * @param handler
     * @return
     * @throws java.io.IOException
     */
    public HttpServer createHttpServer(HttpHandler handler) throws Exception {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(HOST, PORT), 100);
            server.createContext(CONTEXT, handler);
            server.setExecutor(Executors.newCachedThreadPool());
            server.start();
            logger.info("Mock HttpServer启动成功, host:{}, port:{}", HOST, PORT);
            return server;
        }
        catch (IOException ioe) {
            if (ioe instanceof BindException) {
                logger.warn(" Address and Port already bind :" + HOST + ":" + PORT);
            } else {
                ioe.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (mockServerEnbale) {
            createHttpServer(new MockHttpResponseHandler());
        }
    }

    public class MockHttpResponseHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange http) throws IOException {

            try {

                Headers heads = http.getRequestHeaders();
                String contentType = heads.get("Content-type").get(0);

                String format = null;
                if (contentType.indexOf(JSON_FORMAT) != -1) {
                    format = JSON_FORMAT;
                } else if (contentType.indexOf(XML_FORMAT) != -1) {
                    format = XML_FORMAT;
                } else {
                    logger.error("the receiveMessage's format not support");
                    return;
                }

                // 1.获得输入流
                InputStream in = http.getRequestBody();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String temp = null;
                StringBuffer sb = new StringBuffer();
                while ((temp = reader.readLine()) != null) {
                    sb.append(temp);
                }
                reader.close();
                in.close();

                String requestMessage = sb.toString();
                logger.info("模拟服务器接收报文内容：" + requestMessage);

                // 2.获取交易码
                String serviceId = null;
                if (format.equals(JSON_FORMAT)) {
                    JSONObject obj = JSONObject.parseObject(requestMessage);
                    serviceId = obj.getString("serviceId");
                } else {
                    try {
                        Document doc = parseXml(requestMessage);
                        NodeList list = doc.getElementsByTagName("serviceId");
                        if (list != null && list.getLength() > 0) {
                            serviceId = list.item(0).getTextContent();
                        }
                    }
                    catch (Exception e) {
                        logger.error("上送报文格式有误！" + sb);
                    }
                }
                logger.info("接口服务代码:" + serviceId);

                String responseMessage = "";

                String filePath = ResourceUtils.CLASSPATH_URL_PREFIX + "mock/http/response/" + serviceId + "." + format;
                File responseFile = ResourceUtils.getFile(filePath);
                InputStream is = null;
                is = new FileInputStream(responseFile);
                responseMessage = inputStream2String(is);

                logger.info("模拟服务器响应报文内容:" + responseMessage);

                http.getResponseHeaders().add("Content-Type", contentType + ";charset=" + ENCODING);
                http.sendResponseHeaders(HttpURLConnection.HTTP_OK, responseMessage.getBytes(ENCODING).length);
                OutputStream out = null;
                try {
                    out = http.getResponseBody();
                    out.write(responseMessage.getBytes(ENCODING));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                	
                	if( null != out){
                		out.flush();
                        out.close();
                	}
                    http.close();
                }

            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }

        public Document parseXml(String xml) {
            InputStream in = null;
            try {
                in = IOUtils.toInputStream(xml, ENCODING);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                IOUtils.closeQuietly(in);
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            Document document = null;
            try {
                document = factory.newDocumentBuilder().parse(in);
            }
            catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return document;
        }
    }

    private String inputStream2String(InputStream in) throws IOException {
        try {
            return IOUtils.toString(in, ENCODING);
        }
        finally {
            IOUtils.closeQuietly(in);
        }
    }
}
