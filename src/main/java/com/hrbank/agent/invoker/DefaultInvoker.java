package com.hrbank.agent.invoker;

import com.hrbank.agent.http.OKHttpUtils;
import com.hrbank.agent.spring.SpringBeanUtils;

import java.lang.reflect.Method;

/**
 * Title:XMLInvoker
 *
 * @author liumenghua
 **/
public class DefaultInvoker<T> extends AbstractInvoker<T> {

    public DefaultInvoker() {
    }

    public static <T> DefaultInvoker newInstance(Class<T> interfaceClass, Method method) {
        DefaultInvoker<T> rm = new DefaultInvoker<>();
        rm.setInterfaceClass(interfaceClass);
        rm.setMethod(method);
        return rm;
    }

    public Object execute(Object[] args) {
        OKHttpUtils okHttpUtils = (OKHttpUtils) SpringBeanUtils.getBean("okHttpUtils");
        return okHttpUtils.postStringParams(this.getHost() + this.getPath(), args[0].toString());
    }

}
