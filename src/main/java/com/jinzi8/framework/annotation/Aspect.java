package com.jinzi8.framework.annotation;

import java.lang.annotation.*;

/**
 * 切面注解
 * @author 博哥
 * @create 2019-03-25 13:45
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
    /**
     * 注解
     */
    Class<? extends Annotation> value();
}
