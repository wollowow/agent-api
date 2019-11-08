package com.hrbank.agent.spring;


import com.hrbank.agent.annotation.ApiScan;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * Title:ApiAnnotationScannerRegistrar
 *
 * @author liumenghua
 **/
public class ApiAnnotationScannerRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {
    private ResourceLoader resourceLoader;

    public ApiAnnotationScannerRegistrar() {
    }

    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes annoAttrs =
                AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(ApiScan.class.getName()));
        ClassPathRemoteClientScanner scanner = new ClassPathRemoteClientScanner(registry);
        if (this.resourceLoader != null) {
            scanner.setResourceLoader(this.resourceLoader);
        }

        Class<? extends Annotation> annotationClass = annoAttrs.getClass("annotationClass");
        if (!Annotation.class.equals(annotationClass)) {
            scanner.setAnnotationClass(annotationClass);
        }

        Class<?> markerInterface = annoAttrs.getClass("markerInterface");
        if (!Class.class.equals(markerInterface)) {
            scanner.setMarkerInterface(markerInterface);
        }

        List<String> basePackages = new ArrayList();
        String[] var8 = annoAttrs.getStringArray("value");
        int var9 = var8.length;

        int var10;
        String pkg;
        for (var10 = 0; var10 < var9; ++var10) {
            pkg = var8[var10];
            if (StringUtils.hasText(pkg)) {
                basePackages.add(pkg);
            }
        }

        var8 = annoAttrs.getStringArray("basePackages");
        var9 = var8.length;

        for (var10 = 0; var10 < var9; ++var10) {
            pkg = var8[var10];
            if (StringUtils.hasText(pkg)) {
                basePackages.add(pkg);
            }
        }

        scanner.registerFilters();
        scanner.doScan(StringUtils.toStringArray(basePackages));
    }

}
