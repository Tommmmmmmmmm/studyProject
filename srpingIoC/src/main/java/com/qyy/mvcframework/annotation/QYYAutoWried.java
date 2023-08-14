package com.qyy.mvcframework.annotation;

import java.lang.annotation.*;

/**
 * @author Qyy
 * @date 2023/8/9 17:22
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QYYAutoWried {
    String value() default "";
}
