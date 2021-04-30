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

    public Double getPrice() {
        return price;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public IncommingTick(String instrument, Double price,  Long timestamp)
    {
        this.setInstrument(instrument);
        this.setPrice(price);
        this.setTimestamp(timestamp);
    }
}
