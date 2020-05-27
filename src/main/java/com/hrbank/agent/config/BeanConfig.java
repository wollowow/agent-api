package com.hrbank.agent.config;

import com.hrbank.agent.http.OKHttpUtils;
import com.hrbank.agent.spring.SpringBeanUtils;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

/**
 * Title:BeanConfig
 *
 * @author liumenghua
 **/

@Configuration
@Import(SpringBeanUtils.class)
public class BeanConfig {

    @Resource
    private Environment environment;

    @Bean
    public OkHttpClient okHttpClient() {
        String connectTimeoutStr = environment.getProperty("ok-http-client.connect.timeout");
        String readTimeoutStr = environment.getProperty("ok-http-client.read.timeout");
        String writeTimeoutStr = environment.getProperty("ok-http-client.write.timeout");

        long readTimeout = StringUtils.isEmpty(readTimeoutStr) ? 30L : Long.parseLong(readTimeoutStr);
        long writeTimeout = StringUtils.isEmpty(writeTimeoutStr) ? 30L : Long.parseLong(writeTimeoutStr);
        long connectTimeout = StringUtils.isEmpty(connectTimeoutStr) ? 30L : Long.parseLong(connectTimeoutStr);

        return new OkHttpClient.Builder()
                .retryOnConnectionFailure(false)
                .connectionPool(pool())
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .writeTimeout(writeTimeout, TimeUnit.SECONDS)
                .build();
    }

    /**
     * Create a new connection pool with tuning parameters appropriate for a single-user application.
     * The tuning parameters in this pool are subject to change in future OkHttp releases. Currently
     */
    @Bean
    public ConnectionPool pool() {
        return new ConnectionPool(200, 5, TimeUnit.MINUTES);
    }

    @Bean
    @DependsOn("okHttpClient")
    public OKHttpUtils okHttpUtils() {
        return new OKHttpUtils();
    }
}
