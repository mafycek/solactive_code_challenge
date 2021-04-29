package com.solactive.solactive_code_challenge.models.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;

public class IncommingTick {
    @JsonProperty("instrument") private String instrument;

    @JsonProperty("price") private Double price;

    @JsonProperty("timestamp") private Long timestamp;

    public String getInstrument()
    {
        return instrument;
    }

    public Long getTimestamp() {
        return timestamp;
    }
}
