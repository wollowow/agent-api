package com.hrbank.agent.spring;

import com.hrbank.agent.proxy.ApiConfiguration;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

public class ClassPathRemoteClientScanner extends ClassPathBeanDefinitionScanner {
    private Class<? extends Annotation> annotationClass;
    private Class<?> markerInterface;

    public ClassPathRemoteClientScanner(BeanDefinitionRegistry registry) {
        super(registry, false);
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(ApiConfiguration.class);
        GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();
        definition.setAutowireMode(2);
        registry.registerBeanDefinition("remoteClientConfiguration", definition);
    }

    public void setAnnotationClass(Class<? extends Annotation> annotationClass) {
        this.annotationClass = annotationClass;
    }

    public void setMarkerInterface(Class<?> markerInterface) {
        this.markerInterface = markerInterface;
    }

    public void registerFilters() {
        boolean acceptAllInterfaces = true;
        if (this.annotationClass != null) {
            this.addIncludeFilter(new AnnotationTypeFilter(this.annotationClass));
            acceptAllInterfaces = false;
        }

        if (this.markerInterface != null) {
            this.addIncludeFilter(new AssignableTypeFilter(this.markerInterface) {
                protected boolean matchClassName(String className) {
                    return false;
                }
            });
            acceptAllInterfaces = false;
        }

        if (acceptAllInterfaces) {
            this.addIncludeFilter((metadataReader, metadataReaderFactory) -> true);
        }

        this.addExcludeFilter((metadataReader, metadataReaderFactory) -> {
            String className = metadataReader.getClassMetadata().getClassName();
            return className.endsWith("package-info");
        });
    }

    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
        if (beanDefinitions.isEmpty()) {
            this.logger.warn("No Remote client was found in '" + Arrays.toString(basePackages) + "' package. Please " +
                    "check your configuration.");
        } else {
            this.processBeanDefinitions(beanDefinitions);
        }

        return beanDefinitions;
    }

    private void processBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitions) {
        GenericBeanDefinition definition;
        for (Iterator var3 = beanDefinitions.iterator(); var3.hasNext(); definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE)) {
            BeanDefinitionHolder holder = (BeanDefinitionHolder) var3.next();
            definition = (GenericBeanDefinition) holder.getBeanDefinition();
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("Creating ApiProxyFactoryBean with name '" + holder.getBeanName() + "' and '" + definition.getBeanClassName() + "' interfaceClass");
            }

            definition.getConstructorArgumentValues().addGenericArgumentValue(definition.getBeanClassName());
            definition.getPropertyValues().add("interfaceClass", definition.getBeanClassName());
            definition.setBeanClass(ApiProxyFactoryBean.class);
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("Enabling autowire by type for ApiProxyFactoryBean with name '" + holder.getBeanName() + "'.");
            }
        }

    }

    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
    }

    protected boolean checkCandidate(String beanName, BeanDefinition beanDefinition) {
        if (super.checkCandidate(beanName, beanDefinition)) {
            return true;
        } else {
            this.logger.warn("Skipping ApiProxyFactoryBean with name '" + beanName + "' and '" + beanDefinition.getBeanClassName() + "' interfaceClass. Bean already defined with the same name!");
            return false;
        }
    }
}
