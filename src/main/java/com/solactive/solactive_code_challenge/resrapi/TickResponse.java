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
    private TickStorageContainer tickStorageContainer;

    @Value("${solactive.time_horizon:60000}")
    static private Long time_horizon;

    @Value("${solactive.lambda:1}")
    static private Double lambdaExponentialDecay;

    public TickResponse() {
        this.tickStorageContainer = new TickStorageContainer();
    }

    @PostMapping("/ticks")
    public ResponseEntity<String> processTick(@RequestBody IncommingTick incommingTick) {
        // actual timestamp
        Long actualTimestamp = System.currentTimeMillis();

        if (actualTimestamp - incommingTick.getTimestamp() < getTimeHorizon()) {
            // incomming tick can be accepted
            this.tickStorageContainer.AddTick(incommingTick, actualTimestamp);

            return new ResponseEntity<>("Tick added.", HttpStatus.CREATED);
        } else {
            // tick cannot be accpted
            return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/statistics/{instrument}")
    public InstrumentStatistics statistics(String instrumentId) {
        // actual timestamp
        Long actualTimestamp = System.currentTimeMillis();

        if (this.tickStorageContainer.doExistInstrument(instrumentId)) {
            // instrument exists in datastructure
            return this.tickStorageContainer.DeliverStatistics(instrumentId, actualTimestamp);
        } else {
            // instrument does not exist in datastructure
            InstrumentStatistics statistics = new InstrumentStatistics();
            return statistics;
        }
    }

    @PostMapping("/statistics")
    public ArrayList<InstrumentStatistics> completeStatistics() {
        // actual timestamp
        Long actualTimestamp = System.currentTimeMillis();

        ArrayList<InstrumentStatistics> statistics = new ArrayList<InstrumentStatistics>();
        for (String instrumentId: this.tickStorageContainer.getAllInstruments()) {
            Instrument instrument = this.tickStorageContainer.getInstrument(instrumentId);
            InstrumentStatistics instrumentStatistics = instrument.getStatistics(actualTimestamp);
            statistics.add(instrumentStatistics);
        }
        return statistics;
    }

    static public void setTimeHorizon(Long time_horizon) {
        TickResponse.time_horizon = time_horizon;
    }

    static public Long getTimeHorizon() {
        return time_horizon;
    }

    static public Double getLambdaExponentialDecay() {
        return lambdaExponentialDecay;
    }

    static public void setLambdaExponentialDecay(Double lambdaExponentialDecay) {
        TickResponse.lambdaExponentialDecay = lambdaExponentialDecay;
    }

    public TickStorageContainer getTickStorageContainer() {
        return tickStorageContainer;
    }
}
