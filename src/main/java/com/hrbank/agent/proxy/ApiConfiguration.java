package com.hrbank.agent.proxy;

import com.hrbank.agent.exception.ApiProxyException;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Title:ApiConfiguration
 *
 * @author liumenghua
 **/
public class ApiConfiguration {

    public static final String LOG_RESULT_MAX_SIZE = "hshc.remoteclient.log.maxResultSize";
    private Environment environment;
    private final Map<Class<?>, ApiProxyFactory<?>> knownInvokers = new ConcurrentHashMap();
    private int maxResultSize4Log;

    public ApiConfiguration() {
    }

    public <T> void addInvoker(Class<T> type) {
        if (type.isInterface()) {
            if (this.hasInvoker(type)) {
                throw new ApiProxyException("Type " + type + " is already known to the RcConfiguration.");
            }

            this.knownInvokers.put(type, new ApiProxyFactory(this, type));
        }

    }

    public <T> T getInvoker(Class<T> type) {
        ApiProxyFactory<T> proxyFactory = (ApiProxyFactory) this.knownInvokers.get(type);
        if (proxyFactory == null) {
            throw new ApiProxyException("Type " + type + " is not known to the RcConfiguration.");
        } else {
            try {
                return proxyFactory.newInstance();
            } catch (Exception var4) {
                throw new ApiProxyException("Error getting remoteClient instance. Cause: " + var4, var4);
            }
        }
    }

    public boolean hasInvoker(Class<?> type) {
        return this.knownInvokers.containsKey(type);
    }

    public Environment getEnvironment() {
        return this.environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
        if (null != environment) {
            String max = environment.getProperty("hshc.remoteclient.log.maxResultSize");
            this.maxResultSize4Log = StringUtils.isEmpty(max) ? 200 : Integer.parseInt(max);
        }

    }

    public int getMaxResultSize4Log() {
        return this.maxResultSize4Log;
    }
}
