package com.hrbank.agent.proxy;

import com.hrbank.agent.annotation.ApiRequestType;
import com.hrbank.agent.annotation.ApiService;
import com.hrbank.agent.exception.ApiProxyException;
import com.hrbank.agent.invoker.AbstractInvoker;
import com.hrbank.agent.invoker.DefaultInvoker;
import com.hrbank.agent.invoker.JSONInvoker;
import com.hrbank.agent.invoker.XMLInvoker;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Title:ApiProxy
 *
 * @author liumenghua
 **/
public class ApiProxy<T> extends AbstractApiProxy implements InvocationHandler, Serializable {

    private Class<T> clientInterface;
    private final Map<Method, AbstractInvoker> methodCache;

    public ApiProxy(ApiConfiguration configuration, Class<T> clientInterface,
                    Map<Method, AbstractInvoker> methodCache) {
        this.configuration = configuration;
        this.clientInterface = clientInterface;
        this.methodCache = methodCache;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Object.class.equals(method.getDeclaringClass())) {
                return method.invoke(this, args);
        } else {
            AbstractInvoker invoker = this.cachedMethod(method);
            return this.execute(invoker, args);
        }
    }

    private AbstractInvoker cachedMethod(Method method) {
        AbstractInvoker invoker = this.methodCache.get(method);
        if (invoker == null) {
            ApiService serviceAnnotation = method.getAnnotation(ApiService.class);
            if (null == serviceAnnotation) {
                throw new ApiProxyException("can not found @ApiService on class: " + method.toGenericString());
            }
            ApiRequestType requestType = serviceAnnotation.requestType();
            switch (requestType) {
                case XML:
                    invoker = XMLInvoker.newInstance(this.clientInterface, method, serviceAnnotation.parser());
                    break;
                case JSON:
                    invoker = JSONInvoker.newInstance(this.clientInterface, method,serviceAnnotation.parser());
                    break;
                default:
                    invoker = DefaultInvoker.newInstance(this.clientInterface, method, serviceAnnotation.parser());
                    break;
            }
            this.parseParameter(method, invoker);
            this.methodCache.put(method, invoker);
        }

        return invoker;
    }

    private void parseParameter(Method method, AbstractInvoker invoker) {
        ApiService serviceAnnotation = method.getAnnotation(ApiService.class);
        if (null == serviceAnnotation) {
            throw new ApiProxyException("can not found @ApiService on class: " + method.toGenericString());
        } else {
            String host = serviceAnnotation.host();
            host = StringUtils.isEmpty(host) ? this.configuration.getEnvironment().getProperty("remote.agent.host") :
                    host;
            String path = serviceAnnotation.value();
            if(!path.startsWith("/")){
                path = "/" + path;
            }
            invoker.setHost(host);
            invoker.setPath(path);
        }
    }

}
