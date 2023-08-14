package com.qyy.mvcframework.annotation;

import java.lang.annotation.*;

/**
 * @author Qyy
 * @date 2023/8/9 18:12
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QYYService {
    String value() default "";
}
