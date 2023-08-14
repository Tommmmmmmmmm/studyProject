package com.qyy.mvcframework.annotation;

import java.lang.annotation.*;

/**
 * @author Qyy
 * @date 2023/8/9 17:36
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QYYRequestMapping {
    String value() default "";
}
