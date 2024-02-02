package com.ame.util;

import com.ame.core.exception.PlatformException;
import com.ame.spring.BeanManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ClassUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassUtils.class);

    public static ParameterizedType getParameterizedTypeByClass(Class<?> originalType) {
        Type superClassType = originalType.getGenericSuperclass();
        if (superClassType instanceof ParameterizedType) {
            return (ParameterizedType)superClassType;
        } else if (superClassType instanceof Class) {
            return getParameterizedTypeByClass((Class<?>)superClassType);
        }
        return null;
    }

    public static Method findBridgeMethodIfExist(Method bridgedMethod) {
        if (bridgedMethod.isBridge()) {
            return bridgedMethod;
        }
        for (Method method : bridgedMethod.getDeclaringClass().getMethods()) {
            if (method.isBridge() && !method.equals(bridgedMethod) && method.getName().equals(bridgedMethod.getName())
                && method.getParameterCount() == bridgedMethod.getParameterCount()) {
                return method;
            }
        }
        return bridgedMethod;
    }

    public static Class getClassByName(String name) throws ClassNotFoundException {
        Map<String, Class> basicType = new HashMap<>();
        basicType.put("int", Integer.TYPE);
        basicType.put("boolean", Boolean.TYPE);
        basicType.put("double", Double.TYPE);
        basicType.put("byte", Byte.TYPE);
        basicType.put("short", Short.TYPE);
        basicType.put("long", Long.TYPE);
        basicType.put("float", Float.TYPE);
        basicType.put("char", Character.TYPE);
        return basicType.get(name) != null ? basicType.get(name) : Class.forName(name);
    }

    public static Object invokeMethod(Object service, String methodName, Class<?>[] paramTypes, Object[] paramValues) {
        Method method = ReflectionUtils.findMethod(service.getClass(), methodName, paramTypes);
        if (method != null) {
            try {
                return method.invoke(service, paramValues);
            } catch (Exception e) {
                PlatformException causeByClass = ExceptionUtils.getCauseByClass(e.getCause(), PlatformException.class);
                if (causeByClass != null) {
                    throw causeByClass;
                } else {
                    LOGGER.error("", e);
                    throw new PlatformException(
                        "Can not invoke method:" + methodName + " in class:" + service.getClass().getName());
                }
            }
        }
        return null;
    }

    public static Object invokeSpringBeanMethod(Class serviceClass, String apiName, Class<?>[] paramTypes,
        Object[] paramValues) {
        return invokeMethod(BeanManager.getService(serviceClass), apiName, paramTypes, paramValues);
    }
}
