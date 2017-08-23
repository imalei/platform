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

package org.platform.core.action.file;

import java.beans.PropertyDescriptor;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.platform.core.action.Action;
import org.platform.core.action.enums.FileOpType;
import org.platform.core.action.enums.FileType;
import org.platform.core.annotation.action.FileDataOperAction;
import org.platform.core.error.enums.ErrorCode;
import org.platform.core.error.exception.ExceptionBuilder;
import org.platform.core.error.exception.PFException;
import org.platform.core.utils.ActionAnnotationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * @classname: ReadFileDataAction
 * @description: TODO(这里用一句话描述这个类的作用)
 */

@Component
public class ReadFileDataAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(ReadFileDataAction.class);

    private static String ENCODING = System.getProperty("file.encoding");

    public <T> List<T> execute(String actionId, String declareClassName, String localFileUri) throws PFException {

        logger.info("[执行action开始]--[actionId:{}]--[调用方:{}]------", actionId, declareClassName);

        Assert.notNull(localFileUri, "the file's uri must be not null");
        logger.info("[actionId:{}]--[调用方:{}]--[文件路径:{}]", actionId, declareClassName, localFileUri);

        FileDataOperAction fileDataOperAction = ActionAnnotationUtils.getAnnotation(declareClassName, actionId,
                FileDataOperAction.class);
        Assert.notNull(fileDataOperAction, "the FileDataOperAction config must be not null");

        String actionName = fileDataOperAction.name();
        logger.info("[actionId:{}]--[调用方:{}]--[actionName:{}]", actionId, declareClassName, actionName);

        FileOpType fileOpType = fileDataOperAction.opType();
        Assert.isTrue(fileOpType == FileOpType.READ, "the action config must set fileOpType value : [READ]");

        int index = localFileUri.lastIndexOf(".");
        String curFileType = localFileUri.substring(index + 1);
        FileType configFileType = fileDataOperAction.fileType();
        Assert.isTrue(configFileType.name().equalsIgnoreCase(curFileType),
                "the config file type must equals current file type");

        List<T> dtoList = new ArrayList<T>();
        try {
            switch (FileType.valueOf(curFileType.toUpperCase())) {
                case TXT:
                    dtoList = parseFileData(fileDataOperAction, localFileUri, "\\|");
                    break;

                case CSV:
                    dtoList = parseFileData(fileDataOperAction, localFileUri, ",");
                    break;

                default:
                    return null;
            }

            logger.info("[actionId:{}]--[调用方:{}]--[文件操作结果:{}]", actionId, declareClassName, dtoList.size());
        }
        catch (Exception e) {
            throw ExceptionBuilder.newException().withError(ErrorCode.ACTION_EXECUTE_FAILD)
                    .withMessageArgs(new Object[] { "读取文件数据" }).withCause(e).build();
        }

        logger.info("[执行action结束]--[actionId:{}]--[调用方:{}]------", actionId, declareClassName);

        return dtoList;

    }

    /**
     * 
     * @title: parseFileData
     * @author leise
     * @description:
     * @date 2016年6月2日 下午2:46:36
     * @throws
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> parseFileData(FileDataOperAction fileDataOperAction, String localFileUri, String separator)
            throws Exception {

        List<T> dtoList = new ArrayList<T>();

        String[] fields = fileDataOperAction.fields();
        Class<?> dtoClass = fileDataOperAction.dtoClass();

        Map<String, PropertyDescriptor> mappedFields = new HashMap<String, PropertyDescriptor>();

        PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(dtoClass);
        for (PropertyDescriptor pd : pds) {
            if (pd.getWriteMethod() != null) {
                mappedFields.put(pd.getName(), pd);
            }
        }

        Path path = Paths.get(localFileUri);
        List<String> lines = Files.readAllLines(path, Charset.forName(ENCODING));

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            Object[] values = line.split(separator);
            Assert.isTrue(values.length == fields.length,
                    "the params and fields number is not matched, please check at the line number:" + i);
            T dto = (T) BeanUtils.instantiate(dtoClass);
            for (int j = 0; j < fields.length; j++) {
                String field = fields[j];
                if (mappedFields.keySet().contains(field)) {
                    PropertyDescriptor pd = mappedFields.get(field);
                    if (pd.getWriteMethod() != null) {
                        pd.getWriteMethod().invoke(dto, values[j]);
                    }
                }
            }
            dtoList.add(dto);
        }

        return dtoList;
    }
}
