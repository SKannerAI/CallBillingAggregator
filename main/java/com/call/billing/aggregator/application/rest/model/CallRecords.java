package com.call.billing.aggregator.application.rest.model;

import java.io.Serializable;
import java.util.List;

public class CallRecords implements Serializable {

    private List<CallRecord> callRecords;

    // Getters and Setters
    public List<CallRecord> getCallRecords() {
        return callRecords;
    }

    public void setCallRecords(List<CallRecord> callRecords) {
        this.callRecords = callRecords;
    }

    @Override
    public String toString() {
        return "CallRecords{" +
                "callRecords=" + callRecords +
                '}';
    }
}
