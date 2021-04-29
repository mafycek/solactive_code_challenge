package com.solactive.solactive_code_challenge.models;


import com.fasterxml.jackson.annotation.JsonIgnore;

public class IncommingTick {
    @JsonIgnore
    private String instrument;

    private Double price;

    //@NotNull
    private Long timestamp;

    String getInstrument()
    {
        return instrument;
    }

    Long getTimestamp() {
        return timestamp;
    }
}
