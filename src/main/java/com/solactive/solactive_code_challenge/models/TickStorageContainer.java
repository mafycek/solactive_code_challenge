package com.solactive.solactive_code_challenge.models;

import java.util.HashMap;
import java.util.Set;

public class TickStorageContainer {

    HashMap<String, Instrument> dataTicks = new HashMap<String, Instrument>();

    public TickStorageContainer() {
    }

    public Boolean doExistInstrument(String instrumentId) {
        return dataTicks.containsKey(instrumentId);
    }

    public Instrument getInstrument(String instrumentId) {
        return dataTicks.get(instrumentId);
    }

    public Set<String> getAllInstruments() {
        return dataTicks.keySet();
    }
}
