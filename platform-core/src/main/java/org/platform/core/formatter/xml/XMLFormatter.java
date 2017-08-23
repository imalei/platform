/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: XMLFormatter.java
 * @package org.platform.core.format.xml
 * @author leise
 * @date 2016年6月28日 下午3:45:05
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.formatter.xml;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.IOUtils;
import org.platform.core.formatter.Formatter;
import org.springframework.stereotype.Component;

/**
 * @classname: XMLFormatter
 * @description: TODO(这里用一句话描述这个类的作用)
 */
@Component
public class XMLFormatter implements Formatter {

    /**
     * 
     * @title: format
     * @author leise
     * @description:
     * @date 2016年6月28日 下午3:47:22
     * @throws
     */
    @Override
    public <T> String format(T source) throws Exception {
        ByteArrayOutputStream os = null;
        String result = null;
        try {
            os = new ByteArrayOutputStream();
            JAXBContext jabxContext = JAXBContext.newInstance(source.getClass().getSuperclass(), source.getClass());
            Marshaller marshaller = jabxContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(source, os);
            result = new String(os.toByteArray(), "utf-8");
            os.close();
        }
        catch (Exception e) {
            throw e;
        }
        finally {
            IOUtils.closeQuietly(os);
        }
        return result;
    }

    /**
     * 
     * @title: unformat
     * @author leise
     * @description:
     * @date 2016年6月28日 下午3:47:28
     * @throws
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T unformat(String str, Class<T> source) throws Exception {
        JAXBContext context = JAXBContext.newInstance(source.getSuperclass(), source);
        Unmarshaller unmarshal = context.createUnmarshaller();
        T result = (T) unmarshal.unmarshal(new StringReader(str));
        return result;
    }

}
