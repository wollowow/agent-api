package com.hrbank.agent.annotation;

import com.hrbank.agent.spring.ApiAnnotationScannerRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({ApiAnnotationScannerRegistrar.class})
public @interface ApiScan {

    String[] value() default {};

    //服务扫描路径
    String[] basePackages() default {};

    //配置后只加载带注解的类
    Class<? extends Annotation> annotationClass() default Annotation.class;

    Class<?> markerInterface() default Class.class;
}
