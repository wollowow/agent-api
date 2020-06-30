package com.hrbank.agent.proxy;

import com.hrbank.agent.invoker.AbstractInvoker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

/**
 * Title:AbstractApiProxy
 *
 * @author liumenghua
 **/
public abstract class AbstractApiProxy {

    protected ApiConfiguration configuration;

    public AbstractApiProxy() {
    }

    public Object execute(AbstractInvoker invoker, Object[] args) throws UnsupportedEncodingException {
        Class proxyClass = invoker.getMethod().getClass();
        Logger log = LoggerFactory.getLogger(proxyClass);
        log.info("agent.request:class->{},method->{},param->{}",
                invoker.getMethod().getDeclaringClass().getName(), invoker.getMethod().getName(), args);
        Object result = invoker.execute(args);
        log.info("agent.response:class->{},method->{},result->{}", invoker.getMethod().getDeclaringClass().getName(),
                invoker.getMethod().getName(), result);
        return result;
    }
}
