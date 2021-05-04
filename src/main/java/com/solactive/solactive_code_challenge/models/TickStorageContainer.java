package com.solactive.solactive_code_challenge.models;

import com.solactive.solactive_code_challenge.models.dtos.IncommingTick;
import com.solactive.solactive_code_challenge.models.dtos.InstrumentStatistics;
import com.solactive.solactive_code_challenge.resrapi.TickResponse;

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

    public InstrumentStatistics DeliverStatistics(String instrumentId, Long actualTimestamp) {
        Instrument instrument = this.getInstrument(instrumentId);
        InstrumentStatistics statistics = instrument.getStatistics(actualTimestamp);
        return statistics;
    }

    public void AddTick(IncommingTick incommingTick, Long actualTimestamp) {
        Instrument instrument;
        if (this.doExistInstrument(incommingTick.getInstrument())) {
            // search for existing datastructure
            instrument = this.getInstrument(incommingTick.getInstrument());

        } else {
            // create a new datastructure and add dataset
            instrument = this.createNewInstrument(incommingTick.getInstrument(), TickResponse.getLambdaExponentialDecay());
        }

        try {
            // lock operation on the instance
            instrument.getMutex().lock();

            instrument.addTick(incommingTick.getTimestamp(), incommingTick.getPrice());

            // removing ticks older than time horizon
            instrument.getTickInWindow(actualTimestamp, TickResponse.getTimeHorizon());

            // trigger recalculation
            instrument.recalculationVariables();
        } finally {
            instrument.getMutex().unlock();
        }
    }
}
