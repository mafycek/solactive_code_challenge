package com.solactive.solactive_code_challenge.models;

import com.solactive.solactive_code_challenge.models.dtos.IncommingTick;
import com.solactive.solactive_code_challenge.models.dtos.InstrumentStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class TickStorageContainer {
    private static final Logger LOG = LoggerFactory.getLogger(TickStorageContainer.class);

    @Value("${solactive.time_horizon}")
    public Long time_horizon;

    @Value("${solactive.lambda}")
    private Double lambdaExponentialDecay;

    Map<String, Instrument> dataTicks = new HashMap<String, Instrument>();

    public TickStorageContainer() {
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

    @Async("threadPoolTaskExecutor")
    public void AddTick(IncommingTick incommingTick, Long actualTimestamp) {
        Instrument instrument;
        LOG.debug("Adding new tick {}", incommingTick.getInstrument());

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

    public InstrumentStatistics ProcessSingleStatistics(String instrumentId) {
        LOG.debug("Creating statistics for {}", instrumentId);

        // actual timestamp
        Long actualTimestamp = System.currentTimeMillis();

        if (this.doExistInstrument(instrumentId)) {
            // instrument exists in datastructure
            return this.DeliverStatistics(instrumentId, actualTimestamp);
        } else {
            // instrument does not exist in datastructure
            InstrumentStatistics statistics = new InstrumentStatistics();
            return statistics;
        }
    }

    public ArrayList<InstrumentStatistics> ProcessCompleteStatistics() {
        LOG.debug("Creating Complete statistics");

        // actual timestamp
        Long actualTimestamp = System.currentTimeMillis();

        ArrayList<InstrumentStatistics> statistics = new ArrayList<InstrumentStatistics>();
        for (String instrumentId: this.getAllInstruments()) {
            Instrument instrument = this.getInstrument(instrumentId);
            InstrumentStatistics instrumentStatistics = instrument.getStatistics(actualTimestamp);
            statistics.add(instrumentStatistics);
        }
        return statistics;
    }

    public Double getLambdaExponentialDecay() {
        return this.lambdaExponentialDecay;
    }

    public Long getTimeHorizon() {
        return this.time_horizon;
    }
}
