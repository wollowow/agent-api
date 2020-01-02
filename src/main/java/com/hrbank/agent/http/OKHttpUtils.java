package com.hrbank.agent.http;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import javax.annotation.Resource;

@Data
@Slf4j
public class OKHttpUtils {

    @Resource
    private OkHttpClient okHttpClient;

    /**
     * Post请求发送xml数据....
     * 参数一：请求Url
     * 参数二：请求的xmlString
     */
    public String post(String url, String param, String type, String encode) {
        String responseBody = "";
        RequestBody requestBody = RequestBody.create(MediaType.parse(type + ";charset=" + encode), param);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        try (Response response = okHttpClient.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (Exception e) {
            log.error("OKHttp post error >> url->{},ex : ", url, e);
        }
        return responseBody;
    }

    public String postXmlParams(String url, String xml) {
        return post(url, xml, "application/xml", "utf-8");
    }


    public String postStringParams(String url, String param) {
        return post(url, param, "text/plain", "utf-8");
    }

    public String postJsonParams(String url, String json) {
        return post(url, json, "application/json", "utf-8");
    }

}
