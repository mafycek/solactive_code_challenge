package com.solactive.solactive_code_challenge.resrapi;

import com.solactive.solactive_code_challenge.models.Instrument;
import com.solactive.solactive_code_challenge.models.TickStorageContainer;
import com.solactive.solactive_code_challenge.models.dtos.IncommingTick;
import com.solactive.solactive_code_challenge.models.dtos.InstrumentStatistics;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;


@RestController
@RequestMapping("/")
public class TickResponse {
    @Value("${solactive.time_horizon}")
    private Long time_horizon;

    TickStorageContainer tickStorageContainer;

    public TickResponse() {
        this.tickStorageContainer = new TickStorageContainer();
    }

    @PostMapping("/ticks")
    public ResponseEntity<String> processTick(@RequestBody IncommingTick incommingTick) {
        // actual timestamp
        Long actualTimestamp = System.currentTimeMillis();
        if (actualTimestamp - incommingTick.getTimestamp() < time_horizon) {
            // incomming tick can be accepted
            Instrument instrument;
            if (this.tickStorageContainer.doExistInstrument(incommingTick.getInstrument())) {
                // search for existing datastructure
                instrument = this.tickStorageContainer.getInstrument(incommingTick.getInstrument());

            } else {
                // create a new datastructure and add dataset
                instrument = this.tickStorageContainer.createNewInstrument(incommingTick.getInstrument());
            }

            try {
                // lock operation on the instance
                instrument.getMutex().lock();

                instrument.addTick(incommingTick.getTimestamp(), incommingTick.getPrice());

                // removing ticks older than time horizon
                instrument.getTickInWindow(actualTimestamp, time_horizon);

                // trigger recalculation
                instrument.recalculationVariables();
            } finally {
                instrument.getMutex().unlock();
            }

            return new ResponseEntity<>("Tick added.", HttpStatus.CREATED);
        } else {
            // tick cannot be accpted
            return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/statistics/{instrument}")
    public InstrumentStatistics statistics(String instrumentId) {
        if (this.tickStorageContainer.doExistInstrument(instrumentId)) {
            // instrument exists in datastructure
            Instrument instrument = this.tickStorageContainer.getInstrument(instrumentId);
            InstrumentStatistics statistics = instrument.getStatistics();
            return statistics;
        } else {
            // instrument does not exist in datastructure
            InstrumentStatistics statistics = new InstrumentStatistics();
            return statistics;
        }
    }

    @PostMapping("/statistics")
    public ArrayList<InstrumentStatistics> completeStatistics() {
        ArrayList<InstrumentStatistics> statistics = new ArrayList<InstrumentStatistics>();
        for (String instrumentId: this.tickStorageContainer.getAllInstruments()) {
            Instrument instrument = this.tickStorageContainer.getInstrument(instrumentId);
            InstrumentStatistics instrumentStatistics = instrument.getStatistics();
            statistics.add(instrumentStatistics);
        }
        return statistics;
    }
}
