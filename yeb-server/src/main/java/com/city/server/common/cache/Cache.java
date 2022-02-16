package com.city.server.common.cache;


import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cache {

    // 保存时间，默认半小时
    long expire() default 30 * 1000 * 60;

    // 缓存标识 key
    String name() default "";

}
