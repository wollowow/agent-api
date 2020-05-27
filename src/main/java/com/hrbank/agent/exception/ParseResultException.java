package com.hrbank.agent.exception;

/**
 * Title:ApiProxyException
 *
 * @author liumenghua
 **/
public class ParseResultException extends RuntimeException {
    public ParseResultException() {
    }

    public ParseResultException(String message) {
        super(message);
    }

    public ParseResultException(String message, Throwable cause) {
        super(message, cause);
    }
}
