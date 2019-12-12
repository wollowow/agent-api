package com.hrbank.agent.spring;

import com.hrbank.agent.proxy.ApiConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

public class ApiProxyFactoryBean<T> implements FactoryBean<T>, InitializingBean {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private ApiConfiguration configuration;
    private Class<T> interfaceClass;

    public ApiProxyFactoryBean() {
    }

    public ApiProxyFactoryBean(Class<T> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public T getObject() throws Exception {
        return this.configuration.getInvoker(this.interfaceClass);
    }

    public Class<?> getObjectType() {
        return this.interfaceClass;
    }

    public boolean isSingleton() {
        return true;
    }

    public void afterPropertiesSet() throws Exception {
        ApiConfiguration configuration = this.getConfiguration();
        if (!configuration.hasInvoker(this.interfaceClass)) {
            try {
                configuration.addInvoker(this.interfaceClass);
            } catch (Exception var3) {
                this.logger.error("Error while adding the mapper {} to configuration.", this.interfaceClass, var3);
                throw new IllegalArgumentException(var3);
            }
        }

    }

    public Class<T> getInterfaceClass() {
        return this.interfaceClass;
    }

    public void setInterfaceClass(Class<T> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public ApiConfiguration getConfiguration() {
        return this.configuration;
    }

    public void setConfiguration(ApiConfiguration configuration) {
        this.configuration = configuration != null ? configuration : new ApiConfiguration();
    }
}
