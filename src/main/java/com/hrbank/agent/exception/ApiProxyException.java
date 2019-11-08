package com.hrbank.agent.exception;

/**
 * Title:ApiProxyException
 *
 * @author liumenghua
 **/
public class ApiProxyException extends RuntimeException {
    public ApiProxyException() {
    }

    public ApiProxyException(String message) {
        super(message);
    }

    public ApiProxyException(String message, Throwable cause) {
        super(message, cause);
    }
}
