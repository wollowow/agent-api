package com.hrbank.agent;

import com.hrbank.agent.annotation.ApiScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@ApiScan("com.hrbank.agent.api")
public class AgentApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AgentApiApplication.class, args);
    }

}
