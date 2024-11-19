package com.call.billing.aggregator.application.rest.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

public class Result implements Serializable {
    private int customerId;
    private String date;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("customerId", customerId)
                .append("date", date)
                .append("maxConcurrentCalls", maxConcurrentCalls)
                .append("callIds", callIds)
                .append("timestamp", timestamp)
                .toString();
    }

    private int maxConcurrentCalls;
    private List<String> callIds;
    private long timestamp;

    // Getters and Setters
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getMaxConcurrentCalls() {
        return maxConcurrentCalls;
    }

    public void setMaxConcurrentCalls(int maxConcurrentCalls) {
        this.maxConcurrentCalls = maxConcurrentCalls;
    }

    public List<String> getCallIds() {
        return callIds;
    }

    public void setCallIds(List<String> callIds) {
        this.callIds = callIds;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

}

