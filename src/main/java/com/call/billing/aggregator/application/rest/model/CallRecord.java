package com.call.billing.aggregator.application.rest.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CallRecord implements Comparable<CallRecord>, Serializable {

    @JsonProperty("customerId")
    private int customerId;
    @JsonProperty("callId")
    private String callId;
    @JsonProperty("startTimestamp")
    private long startTimestamp;
    @JsonProperty("endTimestamp")
    private long endTimestamp;

    public CallRecord(int customerId, String callId, long startTimestamp, long endTimestamp) {
        this.customerId = customerId;
        this.callId = callId;
        this.startTimestamp = startTimestamp;
        this.endTimestamp = endTimestamp;
    }



    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("customerId", customerId)
                .append("callId", callId)
                .append("startReadableTimestamp", this.getStartReadableTimestamp())
                .append("endReadableTimestamp", this.getEndReadableTimestamp())
                .append("startTimestamp", startTimestamp)
                .append("endTimestamp", endTimestamp)
                .append("startDate", this.getStartDate())
                .append("endDate", this.getEndDate())
                .toString();
    }

    // Getters and Setters
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCallId() {
        return callId;
    }

    public void setCallId(String callId) {
        this.callId = callId;
    }

    public long getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(long startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public long getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(long endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    /**
     * @param callRecord the object to be compared.
     * @return returns a negative integer, zero, or a positive integer depending on if it is less than, equal to, or greater than the specified object.
     */
    @Override
    public int compareTo(CallRecord callRecord) {

        // Convert epoch timestamps to Instant objects
        Instant instant1 = Instant.ofEpochSecond(this.getStartTimestamp());
        Instant instant2 = Instant.ofEpochSecond(callRecord.getStartTimestamp());

        return instant1.compareTo(instant2);
    }

    public String getStartDate() {
        // Convert epoch timestamp to LocalDate
        LocalDate date = Instant.ofEpochMilli(this.getStartTimestamp())
                .atZone(ZoneId.systemDefault())  // Use system's default time zone
                .toLocalDate();

        // Format the date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
    }

    public String getEndDate() {
        // Convert epoch timestamp to LocalDate
        LocalDate date = Instant.ofEpochMilli(this.getEndTimestamp())
                .atZone(ZoneId.systemDefault())  // Use system's default time zone
                .toLocalDate();

        // Format the date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
    }

    public String getStartReadableTimestamp(){
    return convertEpochToEastern(this.getStartTimestamp());
    }

    public String getEndReadableTimestamp(){
        return convertEpochToEastern(this.getEndTimestamp());
    }

    public static String convertEpochToEastern(long epochTimestamp) {
        // Convert epoch to Instant
        Instant instant = Instant.ofEpochMilli(epochTimestamp);

        // Define the Eastern Time zone
        ZoneId easternZoneId = ZoneId.of("America/New_York");

        // Convert the Instant to ZonedDateTime in Eastern Time
        ZonedDateTime easternDateTime = instant.atZone(easternZoneId);

        // Format the ZonedDateTime to a readable format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS z");
        return easternDateTime.format(formatter);
    }
}
