package com.call.billing.aggregator.application.rest.interceptor;

import com.call.billing.aggregator.application.config.WebConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.crypto.codec.Base64;

import java.io.IOException;

/**
 * This interceptor is used in {@link WebConfig} class to add basic authentication for outgoing REST webservice calls
 * 
 * @author Sandeep Kanparthy
 *
 */
public class CustomAuthorizationInterceptor implements ClientHttpRequestInterceptor {

    // API Token
    private final String apiToken;

    @Value("${hubspot.assessment.api.token.isBearerToken}")
    private final Boolean isBearerToken;

    public CustomAuthorizationInterceptor(String apiToken, Boolean isBearerToken) {
        this.apiToken = apiToken;
        this.isBearerToken = isBearerToken;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        byte[] token = Base64.encode((this.apiToken).getBytes());
        if(isBearerToken){
        request.getHeaders().add("Authorization", "Basic " + this.apiToken);}
        else{
            request.getHeaders().add("x-API-key", this.apiToken);
        }
        return execution.execute(request, body);
    }

}
