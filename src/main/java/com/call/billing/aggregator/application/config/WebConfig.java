package com.call.billing.aggregator.application.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Enables Annotations & does a number of useful things – specifically, in the case of REST, it detects the existence of
 * Jackson on the classpath and automatically creates and registers default JSON and XML converters.
 * 
 * <pre>
 * <mvc:annotation-driven />
 * </pre>
 * 
 * @author Sandeep Kanparthy
 * 
 */
@Configuration
@EnableWebMvc
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "com.call.*")
public class WebConfig implements WebMvcConfigurer {

    @Value("${hubspot.assessment.api.token}")
    private String apiToken;

    public WebConfig() {
        super();
    }

    @Bean
    public RestTemplate restTemplate() {
      //  final RestTemplate restTemplate = new RestTemplate();

     //   restTemplate.getInterceptors().add(new CustomAuthorizationInterceptor(apiToken, false));

      //  restTemplate.getInterceptors().add(new LoggingInterceptor());

        return new RestTemplate();
    }
    /**
     * Returns an instance of ObjectMapper used to print a Java object in JSON
     *
     * @return an instance of ObjectMapper.
     */
    @Bean
    public ObjectMapper objMapper() {
        return new ObjectMapper();
    }
}
