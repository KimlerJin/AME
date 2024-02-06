package com.ame.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(value = {ElementType.METHOD})
@Retention(RUNTIME)
public @interface CacheDelete {

    String key();

}