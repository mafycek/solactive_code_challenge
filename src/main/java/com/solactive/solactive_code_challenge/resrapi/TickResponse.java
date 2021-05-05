package com.solactive.solactive_code_challenge.resrapi;

import com.solactive.solactive_code_challenge.models.TickStorageContainer;
import com.solactive.solactive_code_challenge.models.dtos.IncommingTick;
import com.solactive.solactive_code_challenge.models.dtos.InstrumentStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@RestController
@RequestMapping("/")
public class TickResponse {
    @Autowired
    private TickStorageContainer tickStorageContainer;

    public TickResponse () {
    }

    public void setVariables(Long time_horizon, Double lambdaExponentialDecay) {
        this.tickStorageContainer = new TickStorageContainer();
        this.tickStorageContainer.setTimeHorizon(time_horizon);
        this.tickStorageContainer.setLambdaExponentialDecay(lambdaExponentialDecay);
    }

    @PostMapping("/ticks")
    public ResponseEntity<String> processTick(@RequestBody IncommingTick incommingTick) {
        // actual timestamp
        Long actualTimestamp = System.currentTimeMillis();
        if (actualTimestamp - incommingTick.getTimestamp() < tickStorageContainer.getTimeHorizon()) {
            // incomming tick can be accepted
            this.tickStorageContainer.AddTick(incommingTick, actualTimestamp);

            return new ResponseEntity<>("Tick added.", HttpStatus.CREATED);
        } else {
            // tick cannot be accpted
            return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/statistics/{instrumentId}")
    public InstrumentStatistics statistics(@PathVariable("instrumentId") String instrumentId) {
        InstrumentStatistics answer = this.tickStorageContainer.ProcessSingleStatistics(instrumentId);
        return answer;
    }

    @GetMapping("/statistics")
    public ArrayList<InstrumentStatistics> completeStatistics() {
        return this.tickStorageContainer.ProcessCompleteStatistics();
    }

    public TickStorageContainer getTickStorageContainer() {
        return tickStorageContainer;
    }
}
