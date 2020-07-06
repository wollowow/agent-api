package com.hrbank.agent.annotation;


import com.hrbank.agent.parser.Parser;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ApiService {

    //请求路径
    String path() default "";

    //请求地址
    String host() default "";

    //请求方式
    ApiRequestType requestType() default ApiRequestType.XML;

    //解析器
    Class<? extends Parser> parser() default Parser.class;

    //请求编码
    String encoding() default "UTF-8";

    //解析涉及参数
    String[] args() default {};
}
