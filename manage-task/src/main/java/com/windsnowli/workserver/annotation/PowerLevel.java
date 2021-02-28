package com.windsnowli.workserver.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author windSnowLi
 */

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PowerLevel {
    /**
     * 访问权限标记
     *
     * @return 所需访问权限
     */
    int value() default 0;
}
