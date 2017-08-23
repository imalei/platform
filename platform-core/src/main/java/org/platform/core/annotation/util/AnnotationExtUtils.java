package org.platform.core.annotation.util;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;

public class AnnotationExtUtils {

    public static final Set<Class<?>> FILTER_ANNOTATIONS = new HashSet<Class<?>>();
    static {
        FILTER_ANNOTATIONS.add(Retention.class);
        FILTER_ANNOTATIONS.add(Documented.class);
        FILTER_ANNOTATIONS.add(Inherited.class);
        FILTER_ANNOTATIONS.add(Target.class);
    }

    /**
     * 该方法读取的标注包含如下部分：
     * <ul>
     * <li>{@link Class#getAnnotations()}</li>
     * <li>对每个标注递归{@link java.lang.annotation.Annotation#annotationType()}
     * ，忽略JDK自带的标注</li>
     * </ul>
     * <p>
     * 如果同一种类型的标注出现两次，后声明的将被忽略
     * </p>
     *
     * @param clazz 要获取所有标注的类
     * @return 所有的标注。同种类型的标注，后声明的将被忽略
     */
    public static Set<Annotation> getAnnotations(Class<?> clazz) {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        addClass(clazz, classes);

        Map<Class<? extends Annotation>, Annotation> annotationMap = new HashMap<Class<? extends Annotation>, Annotation>();
        for (Class<?> aClass : classes) {
            Annotation[] annotations = aClass.getAnnotations();
            for (Annotation annotation : annotations) {
                if (FILTER_ANNOTATIONS.contains(annotation.annotationType())) {
                    continue;
                }
                if (annotationMap.get(annotation.annotationType()) == null) {
                    annotationMap.put(annotation.annotationType(), annotation);
                }
            }
        }

        return new HashSet<Annotation>(annotationMap.values());
    }

    public static Set<Annotation> getAnnotations(Method method) {
        Set<Class<?>> classes = new HashSet<Class<?>>();

        Annotation[] annotations = method.getAnnotations();
        Map<Class<? extends Annotation>, Annotation> annotationMap = new HashMap<Class<? extends Annotation>, Annotation>();
        for (Annotation annotation : annotations) {
            if (FILTER_ANNOTATIONS.contains(annotation.annotationType())) {
                continue;
            }
            addClass(annotation.annotationType(), classes);
            annotationMap.put(annotation.annotationType(), annotation);
        }

        for (Class<?> aClass : classes) {
            Annotation[] clazzAnnotations = aClass.getAnnotations();
            for (Annotation annotation : clazzAnnotations) {
                if (FILTER_ANNOTATIONS.contains(annotation.annotationType())) {
                    continue;
                }
                if (annotationMap.get(annotation.annotationType()) == null) {
                    annotationMap.put(annotation.annotationType(), annotation);
                }
            }
        }

        return new HashSet<Annotation>(annotationMap.values());
    }

    public static Map<String, AnnotationAttributes> getAnnotationAttributes(Method method) {
        Map<String, AnnotationAttributes> annotationAttributesMap = new HashMap<String, AnnotationAttributes>();

        Set<Annotation> annotations = getAnnotations(method);
        for (Annotation annotation : annotations) {
            annotationAttributesMap.put(StringUtils.uncapitalize(annotation.annotationType().getSimpleName()),
                    AnnotationUtils.getAnnotationAttributes(annotation, true, true));
        }

        return annotationAttributesMap;
    }

    public static Map<String, AnnotationAttributes> getAnnotationAttributes(Class<?> clazz) {
        Map<String, AnnotationAttributes> annotationAttributesMap = new HashMap<String, AnnotationAttributes>();

        Set<Annotation> annotations = getAnnotations(clazz);
        for (Annotation annotation : annotations) {
            annotationAttributesMap.put(StringUtils.uncapitalize(annotation.annotationType().getSimpleName()),
                    AnnotationUtils.getAnnotationAttributes(annotation, true, true));
        }

        return annotationAttributesMap;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Annotation> T findAnnotation(Class<?> clazz, Class<T> annotationType) {
        Set<Annotation> annotations = getAnnotations(clazz);
        for (Annotation annotation : annotations) {
            if (annotationType.equals(annotation.annotationType())) {
                return (T) annotation;
            }
        }
        return AnnotationUtils.findAnnotation(clazz, annotationType);
    }

    private static void addClass(Class<?> clazz, Set<Class<?>> classes) {
        if (classes.contains(clazz)) {
            return;
        }
        classes.add(clazz);

        for (Class<?> ifc : clazz.getInterfaces()) {
            addClass(ifc, classes);
        }

        Annotation[] annotations = clazz.getAnnotations();
        for (Annotation annotation : annotations) {
            if (FILTER_ANNOTATIONS.contains(annotation.annotationType())) {
                continue;
            }
            addClass(annotation.annotationType(), classes);
        }

        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null && !superClass.equals(Object.class)) {
            addClass(superClass, classes);
        }
    }

}
