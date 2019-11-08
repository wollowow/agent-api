package com.hrbank.agent.annotation;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ApiService {

    String value() default "";

    String host() default "";

    ApiRequestType requestType() default ApiRequestType.XML;

    String description() default "";
}