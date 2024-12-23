package com.call.billing.aggregator.application;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class CallBillingAggregatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(CallBillingAggregatorApplication.class, args);
    }

}
