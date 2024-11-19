package com.call.billing.aggregator.application.rest.impl;

import com.call.billing.aggregator.application.rest.HubSpotRestService;
import com.call.billing.aggregator.application.rest.model.CallRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service("getRestClientService")
public class GetRestClientServiceImpl implements HubSpotRestService {
    final static Logger log = LoggerFactory.getLogger("default.activity.logger");

    @Autowired
    RestTemplate restTemplate;

    @Value("${hubspot.assessment.get.api.url}")
    private String hubspotGetEndPoint;

    /**
     * @param input
     * @return String
     */
    @Override
    public CallRecords execute(String input) {

        URI urlWithQuery = UriComponentsBuilder.fromUriString(hubspotGetEndPoint).queryParam("userKey", input).build().toUri();

        ResponseEntity<CallRecords> resp = restTemplate.exchange(RequestEntity.get(urlWithQuery).build(),
                CallRecords.class);

        return resp.getBody();
    }
}
