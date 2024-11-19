package com.call.billing.aggregator.application.rest.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Results implements Serializable {
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("results", results)
                .toString();
    }

    private List<Result> results = new ArrayList<>();

    // Getters and Setters
    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

}

