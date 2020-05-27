package com.hrbank.agent.exception;

/**
 * Title:ApiProxyException
 *
 * @author liumenghua
 **/
public class NullParamException extends RuntimeException {
    public NullParamException() {
    }

    public NullParamException(String message) {
        super(message);
    }

    public NullParamException(String message, Throwable cause) {
        super(message, cause);
    }
}
