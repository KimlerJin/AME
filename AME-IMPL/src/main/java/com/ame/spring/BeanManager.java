package com.ame.spring;

import com.ame.spring.converters.LocalDate2DateConverter;
import com.ame.spring.converters.Number2BooleanConverter;
import com.ame.spring.converters.XMLGregorianCalendar2DateConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultBeanNameGenerator;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.boot.context.event.SpringApplicationEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.core.env.Environment;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Map;

public class BeanManager implements ApplicationListener<SpringApplicationEvent> {

    private static ConfigurableApplicationContext APPLICATION_CONTEXT = null;

    private static ConfigurableConversionService CONVERSION_SERVICE = null;

    private static SpringApplication SPRING_APPLICATION = null;

    public BeanManager() {
    }

    public static String getSpringApplicationName() {
        return APPLICATION_CONTEXT.getEnvironment().getProperty("spring.application.name");
    }

    public static <T> T getService(Class<T> classType) {
        return APPLICATION_CONTEXT.getBean(classType);
    }

    public static <T> T getService(String name, Class<T> classType) {
        return APPLICATION_CONTEXT.getBean(name, classType);
    }

    public static BeanDefinition beanDefinitionForBeanNameIncludingAncestors(String beanName) {
        ConfigurableListableBeanFactory listableBeanFactory =
                (ConfigurableListableBeanFactory) APPLICATION_CONTEXT.getAutowireCapableBeanFactory();
        return beanDefinitionForBeanNameIncludingAncestors(listableBeanFactory, beanName);
    }

    public static String[] beanNamesForTypeIncludingAncestors(Class<?> type) {
        return BeanFactoryUtils.beanNamesForTypeIncludingAncestors(
                (ListableBeanFactory) APPLICATION_CONTEXT.getAutowireCapableBeanFactory(), type);
    }

    public static String[] beanNamesForAnnotationIncludingAncestors(Class<? extends Annotation> annotationType) {
        return BeanFactoryUtils.beanNamesForAnnotationIncludingAncestors(
                (ListableBeanFactory) APPLICATION_CONTEXT.getAutowireCapableBeanFactory(), annotationType);
    }

    public static <T> Map<String, T> beansOfTypeIncludingAncestors(Class<T> classType) {
        return BeanFactoryUtils.beansOfTypeIncludingAncestors(
                (ListableBeanFactory) APPLICATION_CONTEXT.getAutowireCapableBeanFactory(), classType);
    }

    public static <T> T getService(Class<T> classType, Object... args) {
        return APPLICATION_CONTEXT.getBean(classType, args);
    }

    public static <T> Collection<? extends T> getServices(Class<T> classType) {
        Map<String, T> beansOfType = BeanFactoryUtils.beansOfTypeIncludingAncestors(
                (ListableBeanFactory) APPLICATION_CONTEXT.getAutowireCapableBeanFactory(), classType);
        return beansOfType.values();
    }

    public static Environment getEnvironment() {
        return APPLICATION_CONTEXT.getEnvironment();
    }

    public static void publishEvent(Object event) {
        APPLICATION_CONTEXT.publishEvent(event);
    }

    public static ObjectMapper getObjectMapper() {
        return APPLICATION_CONTEXT.getBean(ObjectMapper.class);
    }

    public static ApplicationContext getApplicationContext() {
        return APPLICATION_CONTEXT;
    }

    public static DefaultListableBeanFactory getBeanFactory() {
        return (DefaultListableBeanFactory) APPLICATION_CONTEXT.getBeanFactory();
    }

    public static ConversionService getConversionService() {
        return CONVERSION_SERVICE;
    }

    private static BeanDefinition
    beanDefinitionForBeanNameIncludingAncestors(ConfigurableListableBeanFactory beanFactory, String beanName) {
        try {
            return beanFactory.getBeanDefinition(beanName);
        } catch (NoSuchBeanDefinitionException e) {
            BeanFactory parentBeanFactory = beanFactory.getParentBeanFactory();
            if (parentBeanFactory != null
                    && ConfigurableListableBeanFactory.class.isAssignableFrom(parentBeanFactory.getClass())) {
                return beanDefinitionForBeanNameIncludingAncestors((ConfigurableListableBeanFactory) parentBeanFactory,
                        beanName);
            }
            return null;
        }
    }

    @Override
    public void onApplicationEvent(SpringApplicationEvent event) {
        if (SPRING_APPLICATION == null && event instanceof ApplicationStartingEvent) {
            SPRING_APPLICATION = event.getSpringApplication();
            SPRING_APPLICATION.setBeanNameGenerator(new DefaultBeanNameGenerator());
            System.setProperty("spring.cloud.bootstrap.enabled", "false");
        }
        if (event instanceof ApplicationContextInitializedEvent
                && event.getSpringApplication().equals(SPRING_APPLICATION)) {
            APPLICATION_CONTEXT = ((ApplicationContextInitializedEvent) event).getApplicationContext();
            CONVERSION_SERVICE =
                    (ConfigurableConversionService) APPLICATION_CONTEXT.getBeanFactory().getConversionService();
            CONVERSION_SERVICE.addConverter(new LocalDate2DateConverter());
            CONVERSION_SERVICE.addConverter(new Number2BooleanConverter());
            CONVERSION_SERVICE.addConverter(new XMLGregorianCalendar2DateConverter());

        }
    }

    public static String getIndex() {
        return getEnvironment().getProperty("ame.security.common.index-url");
    }
    public static String getMobileIndex() {
        return getEnvironment().getProperty("ame.security.common.mobile.index-url");
    }
}
