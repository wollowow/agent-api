package com.hrbank.agent.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hrbank.agent.exception.NullParamException;
import org.springframework.util.StringUtils;

/**
 * Title:DefaultJSONParser
 * json默认解析方式
 *
 * @author liumenghua
 **/
public class DefaultJSONParser<T> implements Parser<T> {

    @Override
    public T parserResult(String result, Class<T> tClass, String... args) {

        if (StringUtils.isEmpty(result)) {
            throw new NullParamException("null param to parse!");
        }
        JSONObject jsonObject = JSON.parseObject(result);

        return jsonObject.toJavaObject(tClass);
    }
}
