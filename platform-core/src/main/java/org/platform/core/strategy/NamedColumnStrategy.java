/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: NamedColumnStrategy.java
 * @package org.platform.core.strategy
 * @author leise
 * @date 2016年4月8日 下午12:17:15
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.strategy;

import java.util.Locale;

import org.springframework.util.StringUtils;

/**
 * @classname: NamedColumnStrategy
 * @description: TODO(这里用一句话描述这个类的作用)
 */

public class NamedColumnStrategy {

    /** 数据域驼峰模式转换成下划线，转换为小写字母 约定大于配置 Field:custNo =>>> Column:cust_no */
    public static final String NAMED_UNDERSCORE_STRATEGY = "1";

    /**
     * @title: getDefaultColumnName
     * @author leise
     * @description:
     * @date 2016年4月8日 下午5:51:39
     * @throws
     */

    public static String getDefaultColumnName(String name) {
        // TODO Auto-generated method stub
        return getColumnName(name, NAMED_UNDERSCORE_STRATEGY);
    }

    /**
     * @title: getColumnName
     * @author leise
     * @description:
     * @date 2016年4月8日 下午5:51:39
     * @throws
     */

    public static String getColumnName(String name, String namedStrategy) {
        // TODO Auto-generated method stub
        if (NAMED_UNDERSCORE_STRATEGY.equals(namedStrategy)) {
            String columnName = underscoreName(name);
            return columnName;
        }
        return null;
    }

    /**
     * Convert a name in camelCase to an underscored name in lower case. Any
     * upper case letters are converted to lower case with a preceding
     * underscore.
     * 
     * @param name the original name
     * @return the converted name
     * @since 4.2
     * @see #lowerCaseName
     */
    protected static String underscoreName(String name) {
        if (!StringUtils.hasLength(name.trim())) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        result.append(lowerCaseName(name.substring(0, 1)));
        for (int i = 1; i < name.length(); i++) {
            String s = name.substring(i, i + 1);
            String slc = lowerCaseName(s);
            if (!s.equals(slc)) {
                result.append("_").append(slc);
            } else {
                result.append(s);
            }
        }
        return result.toString();
    }

    /**
     * Convert the given name to lower case. By default, conversions will happen
     * within the US locale.
     * 
     * @param name the original name
     * @return the converted name
     * @since 4.2
     */
    protected static String lowerCaseName(String name) {
        return name.toLowerCase(Locale.US);
    }

}
