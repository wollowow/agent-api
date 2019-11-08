package com.hrbank.agent.invoker;

import lombok.Data;

import java.lang.reflect.Method;

@Data
public abstract class AbstractInvoker<T> {
    private Class<T> interfaceClass;
    private Method method;
    private String host;
    private String path;

    public AbstractInvoker() {
    }

    public abstract Object execute(Object[] var1);
}
