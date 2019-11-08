package com.hrbank.agent.proxy;

import com.hrbank.agent.invoker.AbstractInvoker;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Title:ApiProxyFactory
 *
 * @author liumenghua
 **/
public class ApiProxyFactory<T> {

    private final ApiConfiguration configuration;
    private final Class<T> clientInterface;
    private final Map<Method, AbstractInvoker> methodCache = new ConcurrentHashMap();

    public ApiProxyFactory(ApiConfiguration configuration, Class<T> clientInterface) {
        this.configuration = configuration;
        this.clientInterface = clientInterface;
    }

    public Class<T> getClientInterface() {
        return this.clientInterface;
    }

    public Map<Method, AbstractInvoker> getMethodCache() {
        return this.methodCache;
    }

    protected T newInstance(ApiProxy<T> apiProxy) {
        return (T) Proxy.newProxyInstance(this.clientInterface.getClassLoader(), new Class[]{this.clientInterface},
                apiProxy);
    }

    public T newInstance() {
        ApiProxy<T> proxy = new ApiProxy(this.configuration, this.clientInterface, this.methodCache);
        return this.newInstance(proxy);
    }
}
