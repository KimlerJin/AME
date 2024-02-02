package com.ame.annotation;

import java.lang.annotation.*;

/**
 */
@Inherited
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreEntityQuery {}
