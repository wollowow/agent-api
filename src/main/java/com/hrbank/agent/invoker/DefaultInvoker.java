package com.hrbank.agent.invoker;

import com.hrbank.agent.http.OKHttpUtil;
import com.hrbank.agent.parser.Parser;
import com.hrbank.agent.spring.SpringBeanUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * Title:XMLInvoker
 *
 * @author liumenghua
 **/
@Slf4j
public class DefaultInvoker<T> extends AbstractInvoker<T> {

    private DefaultInvoker() {
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
        OKHttpUtil okHttpUtil = (OKHttpUtil) SpringBeanUtils.getBean("okHttpUtil");
        String param = null == args ? "" : args[0].toString();
        String result = okHttpUtil.postStringParams(this.getHost() + this.getPath(), param, this.getEncoding());
        return parseResult(result);
    }

}
