package com.solactive.solactive_code_challenge.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TickStorageContainer {

    Map<String, Instrument> dataTicks = new HashMap<String, Instrument>();

    public TickStorageContainer() {
    }

    public Boolean doExistInstrument(String instrumentId) {
        return dataTicks.containsKey(instrumentId);
    }

    public Instrument getInstrument(String instrumentId) {
        return dataTicks.get(instrumentId);
    }

    public Instrument createNewInstrument(String instrumentId, Double lambdaExponentialDecay) {
        Instrument instrument = new Instrument(instrumentId, lambdaExponentialDecay);
        dataTicks.put(instrumentId, instrument);
        return instrument;
    }

    public Set<String> getAllInstruments() {
        return dataTicks.keySet();
    }

}
