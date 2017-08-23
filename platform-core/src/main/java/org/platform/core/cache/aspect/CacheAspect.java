/**
 * Copyright (C) 2015-2016 版权所有者为个人leise所有。本软件未经leise授权擅自复制或传播本程序的部分或全部将是非法的。
 * 
 * @title: CacheAspect.java
 * @package org.platform.core.cache.aop
 * @author leise
 * @date 2016年8月4日 下午9:33:16
 * @version v1.00
 * @description: TODO(用一句话描述该文件做什么)
 */

package org.platform.core.cache.aspect;

import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.platform.core.cache.annotation.Cacheable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

/**
 * @classname: CacheAspect
 * @description: TODO(这里用一句话描述这个类的作用)
 */

@Aspect
@Component
public class CacheAspect implements ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(CacheAspect.class);

    @Value(value = "${platform.cache.enable:false}")
    private boolean enableCache;

    private ApplicationContext applicationContext;

    ExpressionParser parser = new SpelExpressionParser();

    @Around("@annotation(cacheable)")
    public Object cacheable(final ProceedingJoinPoint pjp, Cacheable cacheable) throws Throwable {

        Object value = null;

        if (!enableCache) {
            logger.info("the cache is not useable");
            value = pjp.proceed();
            return value;
        }

        String cacheManagerName = cacheable.cacheManager();
        if (StringUtils.isBlank(cacheManagerName)) {
            logger.info("the cacheManager is not defined");
            value = pjp.proceed();
            return value;
        }

        String cacheName = cacheable.cacheName();
        CacheManager cacheManager = BeanFactoryAnnotationUtils.qualifiedBeanOfType(this.applicationContext,
                CacheManager.class, cacheManagerName);
        Cache cache = cacheManager.getCache(cacheName);

        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Class<?> valueType = methodSignature.getReturnType();

        String key = cacheable.key();
        String cacheKey = null;
        if (StringUtils.isBlank(cacheKey)) {
            cacheKey = genDefaultCacheKey(pjp.getArgs());
        } else {
            Method method = methodSignature.getMethod();
            cacheKey = genCacheKey(key, method, pjp.getArgs());
        }

        try {
            value = cache.get(cacheKey, valueType);
            if (!ObjectUtils.isEmpty(value)) {
                logger.info("get the value from cache, cache name: " + cacheName);
                return value;
            }
        }
        catch (Exception e) {
            value = pjp.proceed();
            return value;

        }

        if (ObjectUtils.isEmpty(value)) {
            value = pjp.proceed();

            if (!ObjectUtils.isEmpty(value)) {
                try {
                    cache.put(cacheKey, value);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return value;
    }
    
    
    private String genDefaultCacheKey(Object[] args) {

        StringBuilder keyBulider = new StringBuilder();
        for (Object obj : args) {
            keyBulider.append(obj.toString());
        }
        return keyBulider.toString();
    }

    /**
     * 获取缓存的key key 定义在注解上，支持SPEL表达式
     *
     * @param pjp
     * @return
     */
    private String genCacheKey(String key, Method method, Object[] args) {

        // 获取被拦截方法参数名列表(使用Spring支持类库)
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paraNameArr = u.getParameterNames(method);

        // SPEL上下文
        StandardEvaluationContext context = new StandardEvaluationContext();
        // 把方法参数放入SPEL上下文中
        for (int i = 0; i < paraNameArr.length; i++) {
            context.setVariable(paraNameArr[i], args[i]);
        }
        return parser.parseExpression(key).getValue(context, String.class);
    }

    /* 
    * title: setApplicationContext
    * description: 
    * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
    */

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;

    }

}
