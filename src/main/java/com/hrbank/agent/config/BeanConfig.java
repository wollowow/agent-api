package com.hrbank.agent.config;

import com.hrbank.agent.annotation.ApiScan;
import com.hrbank.agent.http.OKHttpUtils;
import com.hrbank.agent.spring.SpringBeanUtils;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;

import java.util.concurrent.TimeUnit;

/**
 * Title:BeanConfig
 *
 * @author liumenghua
 **/

@Configuration
@Import(SpringBeanUtils.class)
public class BeanConfig {

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                .retryOnConnectionFailure(false)
                .connectionPool(pool())
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30,TimeUnit.SECONDS)
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
    public OKHttpUtils okHttpUtils(){
        return new OKHttpUtils();
    }
}
