package com.ame.util;

import org.springframework.core.ResolvableType;
import org.springframework.util.ConcurrentReferenceHashMap;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

public class AnnotationUtils extends org.springframework.core.annotation.AnnotationUtils {

    private static final Map<MethodAnnotationCacheKey, Method> findMethodWithAnnotationCache =
        new ConcurrentReferenceHashMap<>(256);

    /**
     * 根据查找类上有指定注解的方法，该方法必须被method重写
     * 
     * @param method
     * @param targetClass
     * @param annotation
     * @param <A>
     * @return
     */
    public static <A extends Annotation> Method findAnnotatedMethod(Method method, Class<?> targetClass,
        Class<A> annotation) {
        MethodAnnotationCacheKey methodAnnotationCacheKey =
            new MethodAnnotationCacheKey(method, targetClass, annotation);
        Method result = findMethodWithAnnotationCache.get(methodAnnotationCacheKey);
        if (result == null) {
            Method resolvedMethod = ClassUtils.findBridgeMethodIfExist(method);
            if (isPresent(method, annotation)) {
                result = method;
            }
            if (result == null && !method.getDeclaringClass().equals(targetClass)) {
                result = searchOnClass(resolvedMethod, targetClass, annotation);
            }

            if (result == null) {
                result = searchOnInterfaces(resolvedMethod, annotation, targetClass.getInterfaces());
            }

            while (result == null) {
                targetClass = targetClass.getSuperclass();
                if (targetClass == null || targetClass == Object.class) {
                    break;
                }
                result = findAnnotatedMethod(method, targetClass, annotation);
            }
            if (result != null) {
                findMethodWithAnnotationCache.put(methodAnnotationCacheKey, result);
            }
        }
        return result;
    }

    private static Method searchOnClass(Method method, Class<?> targetClass, Class<? extends Annotation> annotation) {
        for (Method declaredMethod : targetClass.getMethods()) {
            if (isOverride(method, declaredMethod)) {
                Annotation declaredAnnotation = declaredMethod.getDeclaredAnnotation(annotation);
                return declaredAnnotation == null ? null : declaredMethod;
            }
        }
        return null;
    }

    private static Method searchOnInterfaces(Method method, Class<? extends Annotation> annotation, Class<?>... ifcs) {
        if (ifcs != null) {
            for (Class<?> ifc : ifcs) {
                for (Method declaredMethod : ifc.getMethods()) {
                    if (isOverride(method, declaredMethod)) {
                        Annotation declaredAnnotation = declaredMethod.getDeclaredAnnotation(annotation);
                        return declaredAnnotation == null ? null : declaredMethod;
                    }
                }
            }
        }
        return null;
    }

    private static boolean isOverride(Method method, Method candidate) {
        if (!candidate.getName().equals(method.getName())
            || candidate.getParameterCount() != method.getParameterCount()) {
            return false;
        }
        Class<?>[] paramTypes = method.getParameterTypes();
        if (Arrays.equals(candidate.getParameterTypes(), paramTypes)) {
            return true;
        }
        for (int i = 0; i < paramTypes.length; i++) {
            if (paramTypes[i] != ResolvableType.forMethodParameter(candidate, i, method.getDeclaringClass())
                .resolve()) {
                return false;
            }
        }
        return true;
    }

    private static boolean isPresent(Method method, Class<? extends Annotation> annotationType) {
        return method.getDeclaredAnnotation(annotationType) != null;
    }

    private static final class MethodAnnotationCacheKey {

        private final Method method;
        private final Class<? extends Annotation> annotationType;
        private Class<?> targetClass;

        public MethodAnnotationCacheKey(Method method, Class<?> targetClass,
            Class<? extends Annotation> annotationType) {
            this.method = method;
            this.targetClass = targetClass;
            this.annotationType = annotationType;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            MethodAnnotationCacheKey that = (MethodAnnotationCacheKey)o;

            if (!method.equals(that.method)) {
                return false;
            }
            if (!targetClass.equals(that.targetClass)) {
                return false;
            }
            return annotationType.equals(that.annotationType);
        }

        @Override
        public int hashCode() {
            int result = method.hashCode();
            result = 31 * result + targetClass.hashCode();
            result = 31 * result + annotationType.hashCode();
            return result;
        }
    }

}
