package com.hrbank.agent.invoker;

import com.hrbank.agent.http.OKHttpUtils;
import com.hrbank.agent.spring.SpringBeanUtils;

import java.lang.reflect.Method;

/**
 * Title:XMLInvoker
 *
 * @author liumenghua
 **/
public class JSONInvoker<T> extends AbstractInvoker<T> {

    public JSONInvoker() {
    }

    public static <T> JSONInvoker newInstance(Class<T> interfaceClass, Method method) {
        JSONInvoker<T> rm = new JSONInvoker<>();
        rm.setInterfaceClass(interfaceClass);
        rm.setMethod(method);
        return rm;
    }

    public Object execute(Object[] args) {
        OKHttpUtils okHttpUtils = (OKHttpUtils) SpringBeanUtils.getBean("okHttpUtils");

        return okHttpUtils.postJsonParams(this.getHost() + this.getPath(), args[0].toString());
    }

}
