package com.hrbank.agent.invoker;

import com.hrbank.agent.exception.ParseResultException;
import com.hrbank.agent.http.OKHttpUtils;
import com.hrbank.agent.parser.Parser;
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

    public static <T> XMLInvoker newInstance(Class<T> interfaceClass, Method method, Class<? extends Parser> parser) {
        XMLInvoker<T> rm = new XMLInvoker<>();
        rm.setInterfaceClass(interfaceClass);
        rm.setParser(parser);
        rm.setMethod(method);
        return rm;
    }

    public Object execute(Object[] args) {
        OKHttpUtils okHttpUtils = (OKHttpUtils) SpringBeanUtils.getBean("okHttpUtils");
        String result = okHttpUtils.postXmlParams(this.getHost() + this.getPath(), args[0].toString());
        if (Parser.class == this.getParser()) {
            return result;
        }
        try {
            return this.getParser().newInstance().parserResult(result, this.getMethod().getReturnType());
        } catch (Exception e) {
            throw new ParseResultException("http result parse fail", e);
        }
    }

}
