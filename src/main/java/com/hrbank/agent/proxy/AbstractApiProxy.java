package com.hrbank.agent.proxy;

import com.hrbank.agent.invoker.AbstractInvoker;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;

/**
 * Title:AbstractApiProxy
 *
 * @author liumenghua
 **/
@Slf4j
public abstract class AbstractApiProxy {

    protected ApiConfiguration configuration;

    public AbstractApiProxy() {
    }

    public Object execute(AbstractInvoker invoker, Object[] args) throws UnsupportedEncodingException {
        log.info("agent.request:class->{},method->{},param->{}",
                invoker.getMethod().getDeclaringClass().getName(), invoker.getMethod().getName(), args);
        Object result = invoker.execute(args);
        log.info("agent.response.object:class->{},method->{},result->{}",
                invoker.getMethod().getDeclaringClass().getName(),
                invoker.getMethod().getName(), result);
        return result;
    }
}
