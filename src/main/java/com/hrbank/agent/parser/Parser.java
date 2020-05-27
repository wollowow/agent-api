package com.hrbank.agent.parser;

/**
 * @author liumenghua
 * @create 2020-05-27 16:34
 * @desc 解析返回结果
 **/
public interface Parser<T>{


    T parserResult(String result,Class<T> tClass);
}
