package com.call.billing.aggregator.application.rest.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.call.billing.aggregator.application.rest.HubSpotPostRestService;
import com.call.billing.aggregator.application.rest.model.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.net.URI;

@Service("postRestClientService")
public class PostRestClientServiceImpl implements HubSpotPostRestService {

    final static Logger log = LoggerFactory.getLogger("default.activity.logger");

    @Autowired
    private ObjectMapper objMapper;

    @Autowired
    RestTemplate restTemplate;

    @Value("${hubspot.assessment.post.api.url}")
    private String hubspotPostEndPoint;

    /**
     * @param input
     * @return String
     */

    public EmptyOutput execute(Results input) throws Exception {

        String jsonInput = objMapper.writeValueAsString(input);
        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            HttpEntity<String> request = new HttpEntity<>(jsonInput, headers);
            restTemplate.postForObject(new URI(hubspotPostEndPoint), request, String.class);
        } catch (RestClientException e) {
            log.error("POST failed with message {} & root cause = {}", e.getMessage(), e.getRootCause());
            throw new RuntimeException(e.getMessage());
        }
        return null;
    }
}
