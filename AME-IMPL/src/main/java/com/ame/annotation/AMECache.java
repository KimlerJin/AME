package com.ame.annotation;

import java.lang.annotation.*;


@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AMECache {

    String cacheKey();

}
