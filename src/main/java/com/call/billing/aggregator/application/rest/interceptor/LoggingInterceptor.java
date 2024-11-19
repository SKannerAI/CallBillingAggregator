package com.call.billing.aggregator.application.rest.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

/**
 * This class intercepts all request/response made through REST Template. 
 * It is registered in the list of interceptors when building the RestTemplate Client in Config Classes
 * 
 * @author Sandeep Kanparthy
 *
 */
public class LoggingInterceptor implements ClientHttpRequestInterceptor {
	
	final static Logger LOG = LoggerFactory.getLogger("default.activity.logger");

	@Override
	public ClientHttpResponse intercept(HttpRequest req, byte[] reqBody, ClientHttpRequestExecution ex) throws IOException {
		LOG.debug("Request body: {}", new String(reqBody, StandardCharsets.UTF_8));
        ClientHttpResponse response = ex.execute(req, reqBody);
        InputStreamReader isr = new InputStreamReader(response.getBody(), StandardCharsets.UTF_8);
        String body = new BufferedReader(isr)
          .lines()
          .collect(Collectors.joining("\n"));
        LOG.debug("Response body: {}", body);
        return response;
	}

}
