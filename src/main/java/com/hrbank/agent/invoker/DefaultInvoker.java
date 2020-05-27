package com.hrbank.agent.invoker;

import com.hrbank.agent.exception.ParseResultException;
import com.hrbank.agent.http.OKHttpUtils;
import com.hrbank.agent.parser.Parser;
import com.hrbank.agent.spring.SpringBeanUtils;

import java.lang.reflect.Method;
import java.text.ParseException;

/**
 * Title:XMLInvoker
 *
 * @author liumenghua
 **/
public class DefaultInvoker<T> extends AbstractInvoker<T> {

    public DefaultInvoker() {
    }

    public static <T> DefaultInvoker newInstance(Class<T> interfaceClass, Method method,
                                                 Class<? extends Parser> parser) {
        DefaultInvoker<T> rm = new DefaultInvoker<>();
        rm.setInterfaceClass(interfaceClass);
        rm.setParser(parser);
        rm.setMethod(method);
        return rm;
    }

    public Object execute(Object[] args) {
        OKHttpUtils okHttpUtils = (OKHttpUtils) SpringBeanUtils.getBean("okHttpUtils");
        String result = okHttpUtils.postStringParams(this.getHost() + this.getPath(), args[0].toString());
        if (Parser.class == this.getParser()) {
            return result;
        }
        try {
            Class resultType = this.getMethod().getReturnType();
            return this.getParser().newInstance().parserResult(result, resultType);
        } catch (Exception e) {
            throw new ParseResultException("http result parse fail", e);
        }
    }

}
