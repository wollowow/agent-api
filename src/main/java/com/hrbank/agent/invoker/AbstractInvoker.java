package com.hrbank.agent.invoker;

import com.hrbank.agent.parser.Parser;
import lombok.Data;

import java.lang.reflect.Method;

@Data
public abstract class AbstractInvoker<T> {
    private Class<T> interfaceClass;
    private Class<? extends Parser> parser;
    private Method method;
    private String host;
    private String path;

    public AbstractInvoker() {
    }

    public abstract Object execute(Object[] var1);
}
