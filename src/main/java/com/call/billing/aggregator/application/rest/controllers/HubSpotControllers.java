package com.call.billing.aggregator.application.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.call.billing.aggregator.application.rest.HubSpotPostRestService;
import com.call.billing.aggregator.application.rest.HubSpotRestService;
import com.call.billing.aggregator.application.rest.model.CallRecord;
import com.call.billing.aggregator.application.rest.model.CallRecords;
import com.call.billing.aggregator.application.rest.model.Result;
import com.call.billing.aggregator.application.rest.model.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class HubSpotControllers {

    final static Logger log = LoggerFactory.getLogger("default.activity.logger");

    @Autowired
    @Qualifier("getRestClientService")
    HubSpotRestService getRestClientService;

    @Autowired
    @Qualifier("postRestClientService")
    HubSpotPostRestService postRestClientService;

    @Value("${hubspot.assessment.api.token}")
    private String userKey;

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping(method = RequestMethod.GET, value = "/startProcessing", produces = "application/json")
    public @ResponseBody Results startProcessing() throws Exception {
        CallRecords unSortedCallRecords = getRestClientService.execute(userKey);

        // Group by customerId and startDate
        Map<Integer, Map<String, List<CallRecord>>> recordsByCustomerAndDate = unSortedCallRecords.getCallRecords().stream()
                .collect(Collectors.groupingBy(CallRecord::getCustomerId,
                        Collectors.groupingBy(CallRecord::getStartDate)));

        System.out.println(" RawData " + objectMapper.writeValueAsString(recordsByCustomerAndDate));
        // Print it for verification
      /*  for (Map.Entry<Integer, Map<String, List<CallRecord>>> customerEntry : recordsByCustomerAndDate.entrySet()) {
            Integer customerId = customerEntry.getKey();
            System.out.println(" Customer ID: " + customerId);

            for (Map.Entry<String, List<CallRecord>> dateEntry : customerEntry.getValue().entrySet()) {
                String date = dateEntry.getKey();
                System.out.println("  Date: " + date);

                List<CallRecord> callRecords = dateEntry.getValue();
                System.out.println("    Call Records:");

                for (CallRecord record : callRecords) {
                    System.out.println("      " + record);
                }
            }
        }*/

        // Find overlapping and non-overlapping call records
        Map<Integer, Map<String, Map<String, List<CallRecord>>>> groupedRecords = new HashMap<>();

        for (var customerEntry : recordsByCustomerAndDate.entrySet()) {
            int customerId = customerEntry.getKey();
            Map<String, Map<String, List<CallRecord>>> dateGroupMap = new HashMap<>();

            for (var dateEntry : customerEntry.getValue().entrySet()) {
                String date = dateEntry.getKey();
                List<CallRecord> records = dateEntry.getValue();

                // Sort by startTimestamp to check for overlaps
                records.sort((a, b) -> Long.compare(a.getStartTimestamp(), b.getStartTimestamp()));

                List<List<CallRecord>> overlappingGroups = new ArrayList<>();
                List<CallRecord> nonOverlappingRecords = new ArrayList<>();
                List<CallRecord> currentOverlapGroup = new ArrayList<>();
                currentOverlapGroup.add(records.get(0));  // Start with the first record

                for (int i = 1; i < records.size(); i++) {
                    CallRecord prev = records.get(i - 1);
                    CallRecord curr = records.get(i);

                    if (prev.getEndTimestamp() > curr.getStartTimestamp()) {
                        // There is an overlap, add to current overlap group
                        currentOverlapGroup.add(curr);
                    } else {
                        // No overlap
                        if (currentOverlapGroup.size() > 1) {
                            overlappingGroups.add(new ArrayList<>(currentOverlapGroup));
                        } else {
                            nonOverlappingRecords.add(currentOverlapGroup.get(0));
                        }
                        currentOverlapGroup.clear();
                        currentOverlapGroup.add(curr);
                    }
                }

                // Add the last group if it contains more than one record
                if (currentOverlapGroup.size() > 1) {
                    overlappingGroups.add(currentOverlapGroup);
                } else if (!currentOverlapGroup.isEmpty()) {
                    // Add only one non-overlapping record
                    nonOverlappingRecords.add(currentOverlapGroup.get(0));
                }
                // Store results: if there are overlapping groups, store only those
                Map<String, List<CallRecord>> recordGroups = new HashMap<>();
                if (!overlappingGroups.isEmpty()) {
                    for (List<CallRecord> group : overlappingGroups) {
                        recordGroups.put("Overlapping", new ArrayList<>(group));
                    }
                } else {
                    // If no overlapping records exist for the day, store only one non-overlapping record
                    recordGroups.put("Non-Overlapping", List.of(nonOverlappingRecords.get(0)));
                }

                dateGroupMap.put(date, recordGroups);
            }
            groupedRecords.put(customerId, dateGroupMap);
        }

        System.out.println("Sandeep JSON " +objectMapper.writeValueAsString(groupedRecords));

        Results results = new Results();
        Result result;
        // Print overlapping records
        for (var customerEntry : groupedRecords.entrySet()) {

            Integer customerId = customerEntry.getKey();
            System.out.println("Customer ID: " + customerId);

            for (var dateEntry : customerEntry.getValue().entrySet()) {

                String date = dateEntry.getKey();
                System.out.println("  Date: " + date);

                result = new Result();
                result.setCustomerId(customerId);
                result.setDate(date);

                List<String> callIds = new ArrayList<>(2);
                for (var groupEntry : dateEntry.getValue().entrySet()) {
                    String groupType = groupEntry.getKey();
                    System.out.println("    " + groupType + " Calls:");

                    for (CallRecord record : groupEntry.getValue()) {
                        System.out.println("      " + record);
                        callIds.add(record.getCallId());
                        result.setTimestamp(record.getStartTimestamp());
                    }

                    result.setMaxConcurrentCalls(callIds.size());
                    result.setCallIds(callIds);
                } results.getResults().add(result);
            }
        }
        // Post the results
        postRestClientService.execute(results);
        return results;

    }
}
