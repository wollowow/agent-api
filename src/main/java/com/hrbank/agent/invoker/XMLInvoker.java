package com.hrbank.agent.invoker;

import com.hrbank.agent.http.OKHttpUtils;
import com.hrbank.agent.spring.SpringBeanUtils;

import java.lang.reflect.Method;

/**
 * Title:XMLInvoker
 *
 * @author liumenghua
 **/
public class XMLInvoker<T> extends AbstractInvoker<T> {


    public XMLInvoker() {
    }

    public static <T> XMLInvoker newInstance(Class<T> interfaceClass, Method method) {
        XMLInvoker<T> rm = new XMLInvoker<>();
        rm.setInterfaceClass(interfaceClass);
        rm.setMethod(method);
        return rm;
    }

    public Object execute(Object[] args) {
        OKHttpUtils okHttpUtils = (OKHttpUtils) SpringBeanUtils.getBean("okHttpUtils");
        return okHttpUtils.postXmlParams(this.getHost() + this.getPath(), args[0].toString());
    }

}
