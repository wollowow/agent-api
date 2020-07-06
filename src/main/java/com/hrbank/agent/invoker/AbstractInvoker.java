package com.hrbank.agent.invoker;

import com.hrbank.agent.exception.ParseResultException;
import com.hrbank.agent.parser.Parser;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;

@Data
public abstract class AbstractInvoker<T> {
    private Class<T> interfaceClass;
    private Class<? extends Parser> parser;
    private Method method;
    private String host;
    private String path;
    private String encoding;
    private String[] args;

    AbstractInvoker() {
    }

    public abstract Object execute(Object[] var1) throws UnsupportedEncodingException;

    Object parseResult(String result) {
        if (Parser.class == this.getParser() || StringUtils.isEmpty(result)) {
            return result;
        }
        try {
            Class resultType = this.getMethod().getReturnType();
            return this.getParser().newInstance().parserResult(result, resultType, args);
        } catch (Exception e) {
            throw new ParseResultException("http result parse fail", e);
        }
    }
}
