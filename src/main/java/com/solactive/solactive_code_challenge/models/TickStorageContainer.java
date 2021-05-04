package com.solactive.solactive_code_challenge.models;

import com.solactive.solactive_code_challenge.models.dtos.IncommingTick;
import com.solactive.solactive_code_challenge.models.dtos.InstrumentStatistics;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TickStorageContainer {

    private Long time_horizon;
    private Double lambdaExponentialDecay;

    Map<String, Instrument> dataTicks = new HashMap<String, Instrument>();

    public TickStorageContainer(Long time_horizon, Double lambdaExponentialDecay) {
        this.time_horizon = time_horizon;
        this.lambdaExponentialDecay = lambdaExponentialDecay;
    }

    public Boolean doExistInstrument(String instrumentId) {
        return dataTicks.containsKey(instrumentId);
    }

    public Instrument getInstrument(String instrumentId) {
        return dataTicks.get(instrumentId);
    }

    public Instrument createNewInstrument(String instrumentId) {
        Instrument instrument = new Instrument(instrumentId, getTimeHorizon(), getLambdaExponentialDecay());
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
            instrument = this.createNewInstrument(incommingTick.getInstrument());
        }

        try {
            // lock operation on the instance
            instrument.getMutex().lock();

            instrument.addTick(incommingTick.getTimestamp(), incommingTick.getPrice());

            // removing ticks older than time horizon
            Map.Entry<Long, Double> lastEntry = instrument.getTicks().lastEntry();
            instrument.getTickInWindow(lastEntry.getKey(), getTimeHorizon());

            // trigger recalculation
            instrument.recalculationVariables();
        } finally {
            instrument.getMutex().unlock();
        }
    }

    public Double getLambdaExponentialDecay() {
        return lambdaExponentialDecay;
    }

    public Long getTimeHorizon() {
        return time_horizon;
    }
}
