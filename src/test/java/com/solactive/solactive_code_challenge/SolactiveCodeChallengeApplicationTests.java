package com.solactive.solactive_code_challenge;

import com.solactive.solactive_code_challenge.models.dtos.IncommingTick;
import com.solactive.solactive_code_challenge.models.dtos.InstrumentStatistics;
import com.solactive.solactive_code_challenge.resrapi.TickResponse;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import org.testng.annotations.BeforeClass;

//#@RunWith(SpringRunner.class )
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
class SolactiveCodeChallengeApplicationTests {

	@Autowired
	TickResponse tickResponse;

	@BeforeClass
	public void testSetup() {
	}

	@Test
	void singleTest() {
		this.tickResponse = new TickResponse(60000L, 1.0);

		InstrumentStatistics instrumentStatisticsABC = this.tickResponse.statistics("ABC");

		Assert.isTrue(Double.compare(Double.NaN, instrumentStatisticsABC.getAverage()) == 0, "Average");
		Assert.isTrue(Double.compare(Double.NaN, instrumentStatisticsABC.getMaximalDrawdown()) == 0, "Maximal drawdown");
		Assert.isTrue(Double.compare(Double.NaN, instrumentStatisticsABC.getMaximum()) == 0, "Maximum");
		Assert.isTrue(Double.compare(Double.NaN, instrumentStatisticsABC.getMinimum()) == 0, "Minimum");
		Assert.isTrue(Double.compare(Double.NaN, instrumentStatisticsABC.getQuantile_5()) == 0, "Quantile");
		Assert.isTrue(Double.compare(Double.NaN, instrumentStatisticsABC.getTimeExponentiallyWeightedAverage()) == 0, "Time weighted average");
		Assert.isTrue(Double.compare(Double.NaN, instrumentStatisticsABC.getTimeWeightedAverage()) == 0, "Time weighted average");
		Assert.isTrue(Double.compare(Double.NaN, instrumentStatisticsABC.getVolatility()) == 0, "Volatility");
		Assert.isTrue(0L == instrumentStatisticsABC.getCount(), "Count");

		Long actualTimestamp = System.currentTimeMillis();
		IncommingTick incommingTick = new IncommingTick("ABC", 1.0, actualTimestamp);
		this.tickResponse.processTick(incommingTick);

		instrumentStatisticsABC = this.tickResponse.getTickStorageContainer().DeliverStatistics("ABC", actualTimestamp);
		Assert.isTrue(Double.compare(1.0, instrumentStatisticsABC.getAverage()) == 0, "Average");
		Assert.isTrue(Double.compare(0.0, instrumentStatisticsABC.getMaximalDrawdown()) == 0, "Maximal drawdown");
		Assert.isTrue(Double.compare(1.0, instrumentStatisticsABC.getMaximum()) == 0, "Maximum");
		Assert.isTrue(Double.compare(1.0, instrumentStatisticsABC.getMinimum()) == 0, "Minimum");
		Assert.isTrue(Double.compare(1.0, instrumentStatisticsABC.getQuantile_5()) == 0, "Quantile");
		Assert.isTrue(Double.compare(1.0, instrumentStatisticsABC.getTimeExponentiallyWeightedAverage()) == 0, "Time weighted average");
		Assert.isTrue(Double.compare(Double.NaN, instrumentStatisticsABC.getTimeWeightedAverage()) == 0, "Time weighted average");
		Assert.isTrue(Double.compare(0.0, instrumentStatisticsABC.getVolatility()) == 0, "Volatility");
		Assert.isTrue(1L == instrumentStatisticsABC.getCount(), "Count");

		InstrumentStatistics instrumentStatisticsDEF = this.tickResponse.statistics("DEF");
		Assert.isTrue(Double.compare(Double.NaN, instrumentStatisticsDEF.getAverage()) == 0, "Average");
		Assert.isTrue(Double.compare(Double.NaN, instrumentStatisticsDEF.getMaximalDrawdown()) == 0, "Maximal drawdown");
		Assert.isTrue(Double.compare(Double.NaN, instrumentStatisticsDEF.getMaximum()) == 0, "Maximum");
		Assert.isTrue(Double.compare(Double.NaN, instrumentStatisticsDEF.getMinimum()) == 0, "Minimum");
		Assert.isTrue(Double.compare(Double.NaN, instrumentStatisticsDEF.getQuantile_5()) == 0, "Quantile");
		Assert.isTrue(Double.compare(Double.NaN, instrumentStatisticsDEF.getTimeExponentiallyWeightedAverage()) == 0, "Time weighted average");
		Assert.isTrue(Double.compare(Double.NaN, instrumentStatisticsDEF.getTimeWeightedAverage()) == 0, "Time weighted average");
		Assert.isTrue(Double.compare(Double.NaN, instrumentStatisticsDEF.getVolatility()) == 0, "Volatility");
		Assert.isTrue(0L == instrumentStatisticsDEF.getCount(), "Count");

		actualTimestamp += 1;
		incommingTick = new IncommingTick("ABC", 2.0, actualTimestamp);
		this.tickResponse.processTick(incommingTick);

		Double timeExponential1 = (Math.exp(-this.tickResponse.getLambdaExponentialDecay() * 1) * 1 + 2.0) / (Math.exp(-this.tickResponse.getLambdaExponentialDecay() * 1) + 1.0);
		instrumentStatisticsABC = this.tickResponse.getTickStorageContainer().DeliverStatistics("ABC", actualTimestamp);
		Assert.isTrue(Double.compare(1.5, instrumentStatisticsABC.getAverage()) == 0, "Average");
		Assert.isTrue(Double.compare(0.0, instrumentStatisticsABC.getMaximalDrawdown()) == 0, "Maximal drawdown");
		Assert.isTrue(Double.compare(2.0, instrumentStatisticsABC.getMaximum()) == 0, "Maximum");
		Assert.isTrue(Double.compare(1.0, instrumentStatisticsABC.getMinimum()) == 0, "Minimum");
		Assert.isTrue(Double.compare(1.05, instrumentStatisticsABC.getQuantile_5()) == 0, "Quantile");
		Assert.isTrue(Double.compare(timeExponential1, instrumentStatisticsABC.getTimeExponentiallyWeightedAverage()) == 0, "Time weighted average");
		Assert.isTrue(Double.compare(1.0, instrumentStatisticsABC.getTimeWeightedAverage()) == 0, "Time weighted average");
		Assert.isTrue(Double.compare(0.5, instrumentStatisticsABC.getVolatility()) == 0, "Volatility");
		Assert.isTrue(2L == instrumentStatisticsABC.getCount(), "Count");

		instrumentStatisticsDEF = this.tickResponse.statistics("DEF");
		Assert.isTrue(Double.compare(Double.NaN, instrumentStatisticsDEF.getAverage()) == 0, "Average");
		Assert.isTrue(Double.compare(Double.NaN, instrumentStatisticsDEF.getMaximalDrawdown()) == 0, "Maximal drawdown");
		Assert.isTrue(Double.compare(Double.NaN, instrumentStatisticsDEF.getMaximum()) == 0, "Maximum");
		Assert.isTrue(Double.compare(Double.NaN, instrumentStatisticsDEF.getMinimum()) == 0, "Minimum");
		Assert.isTrue(Double.compare(Double.NaN, instrumentStatisticsDEF.getQuantile_5()) == 0, "Quantile");
		Assert.isTrue(Double.compare(Double.NaN, instrumentStatisticsDEF.getTimeExponentiallyWeightedAverage()) == 0, "Time weighted average");
		Assert.isTrue(Double.compare(Double.NaN, instrumentStatisticsDEF.getTimeWeightedAverage()) == 0, "Time weighted average");
		Assert.isTrue(Double.compare(Double.NaN, instrumentStatisticsDEF.getVolatility()) == 0, "Volatility");
		Assert.isTrue(0L == instrumentStatisticsDEF.getCount(), "Count");

		actualTimestamp += 1;
		incommingTick = new IncommingTick("ABC", 0.0, actualTimestamp);
		this.tickResponse.processTick(incommingTick);

		Double timeExponential2 = (Math.exp(-this.tickResponse.getLambdaExponentialDecay() * 1) * 1.0 + Math.exp(-this.tickResponse.getLambdaExponentialDecay() * 1) * 2.0) / (Math.exp(-this.tickResponse.getLambdaExponentialDecay() * 1) + Math.exp(-this.tickResponse.getLambdaExponentialDecay() * 1) + 1.0);
		Double volatility = Math.sqrt(2.0 / 3.0);
		instrumentStatisticsABC = this.tickResponse.getTickStorageContainer().DeliverStatistics("ABC", actualTimestamp);
		Assert.isTrue(Double.compare(1.0, instrumentStatisticsABC.getAverage()) == 0, "Average");
		Assert.isTrue(Double.compare(2.0, instrumentStatisticsABC.getMaximalDrawdown()) == 0, "Maximal drawdown");
		Assert.isTrue(Double.compare(2.0, instrumentStatisticsABC.getMaximum()) == 0, "Maximum");
		Assert.isTrue(Double.compare(0.0, instrumentStatisticsABC.getMinimum()) == 0, "Minimum");
		Assert.isTrue(Double.compare(0.1, instrumentStatisticsABC.getQuantile_5()) == 0, "Quantile");
		Assert.isTrue(Double.compare(timeExponential2, instrumentStatisticsABC.getTimeExponentiallyWeightedAverage()) == 0, "Time weighted average");
		Assert.isTrue(Double.compare(1.5, instrumentStatisticsABC.getTimeWeightedAverage()) == 0, "Time weighted average");
		Assert.isTrue(Double.compare(volatility, instrumentStatisticsABC.getVolatility()) == 0, "Volatility");
		Assert.isTrue(3L == instrumentStatisticsABC.getCount(), "Count");
	}

	@Test
	void reverseOrderTest() {
		this.tickResponse = new TickResponse(60000L, 1.0);

		InstrumentStatistics instrumentStatisticsABC = this.tickResponse.statistics("ABC");

		Long actualTimestamp = System.currentTimeMillis();
		IncommingTick incommingTick = new IncommingTick("ABC", 2.0, actualTimestamp);
		this.tickResponse.processTick(incommingTick);

		actualTimestamp -= 1;
		incommingTick = new IncommingTick("ABC", 1.0, actualTimestamp);
		this.tickResponse.processTick(incommingTick);

		Double timeExponential1 = (Math.exp(-this.tickResponse.getLambdaExponentialDecay() * 1) * 1 + 2.0) / (Math.exp(-this.tickResponse.getLambdaExponentialDecay() * 1) + 1.0);
		instrumentStatisticsABC = this.tickResponse.getTickStorageContainer().DeliverStatistics("ABC", actualTimestamp + 1);
		Assert.isTrue(Double.compare(1.5, instrumentStatisticsABC.getAverage()) == 0, "Average");
		Assert.isTrue(Double.compare(0.0, instrumentStatisticsABC.getMaximalDrawdown()) == 0, "Maximal drawdown");
		Assert.isTrue(Double.compare(2.0, instrumentStatisticsABC.getMaximum()) == 0, "Maximum");
		Assert.isTrue(Double.compare(1.0, instrumentStatisticsABC.getMinimum()) == 0, "Minimum");
		Assert.isTrue(Double.compare(1.05, instrumentStatisticsABC.getQuantile_5()) == 0, "Quantile");
		Assert.isTrue(Double.compare(timeExponential1, instrumentStatisticsABC.getTimeExponentiallyWeightedAverage()) == 0, "Time weighted average");
		Assert.isTrue(Double.compare(1.0, instrumentStatisticsABC.getTimeWeightedAverage()) == 0, "Time weighted average");
		Assert.isTrue(Double.compare(0.5, instrumentStatisticsABC.getVolatility()) == 0, "Volatility");
		Assert.isTrue(2L == instrumentStatisticsABC.getCount(), "Count");

		actualTimestamp += 2;
		incommingTick = new IncommingTick("ABC", 0.0, actualTimestamp);
		this.tickResponse.processTick(incommingTick);

		Double timeExponential2 = (Math.exp(-this.tickResponse.getLambdaExponentialDecay() * 1) * 1.0 + Math.exp(-this.tickResponse.getLambdaExponentialDecay() * 1) * 2.0) / (Math.exp(-this.tickResponse.getLambdaExponentialDecay() * 1) + Math.exp(-this.tickResponse.getLambdaExponentialDecay() * 1) + 1.0);
		Double volatility = Math.sqrt(2.0 / 3.0);
		instrumentStatisticsABC = this.tickResponse.getTickStorageContainer().DeliverStatistics("ABC", actualTimestamp);
		Assert.isTrue(Double.compare(1.0, instrumentStatisticsABC.getAverage()) == 0, "Average");
		Assert.isTrue(Double.compare(2.0, instrumentStatisticsABC.getMaximalDrawdown()) == 0, "Maximal drawdown");
		Assert.isTrue(Double.compare(2.0, instrumentStatisticsABC.getMaximum()) == 0, "Maximum");
		Assert.isTrue(Double.compare(0.0, instrumentStatisticsABC.getMinimum()) == 0, "Minimum");
		Assert.isTrue(Double.compare(0.1, instrumentStatisticsABC.getQuantile_5()) == 0, "Quantile");
		Assert.isTrue(Double.compare(timeExponential2, instrumentStatisticsABC.getTimeExponentiallyWeightedAverage()) == 0, "Time weighted average");
		Assert.isTrue(Double.compare(1.5, instrumentStatisticsABC.getTimeWeightedAverage()) == 0, "Time weighted average");
		Assert.isTrue(Double.compare(volatility, instrumentStatisticsABC.getVolatility()) == 0, "Volatility");
		Assert.isTrue(3L == instrumentStatisticsABC.getCount(), "Count");
	}

	@Test
	void timeManipulationTest() {
		this.tickResponse = new TickResponse(60000L, 1.0);

		InstrumentStatistics instrumentStatisticsABC = this.tickResponse.statistics("ABC");

		Long actualTimestamp = System.currentTimeMillis();
		IncommingTick incommingTick = new IncommingTick("ABC", 1.0, actualTimestamp);
		this.tickResponse.processTick(incommingTick);

		actualTimestamp += 1;
		incommingTick = new IncommingTick("ABC", 2.0, actualTimestamp);
		this.tickResponse.processTick(incommingTick);

		instrumentStatisticsABC = this.tickResponse.getTickStorageContainer().DeliverStatistics("ABC", actualTimestamp);

		Double timeExponential1 = (Math.exp(-this.tickResponse.getLambdaExponentialDecay() * 1) * 1 + 2.0) / (Math.exp(-this.tickResponse.getLambdaExponentialDecay() * 1) + 1.0);
		Assert.isTrue(Double.compare(1.5, instrumentStatisticsABC.getAverage()) == 0, "Average");
		Assert.isTrue(Double.compare(0.0, instrumentStatisticsABC.getMaximalDrawdown()) == 0, "Maximal drawdown");
		Assert.isTrue(Double.compare(2.0, instrumentStatisticsABC.getMaximum()) == 0, "Maximum");
		Assert.isTrue(Double.compare(1.0, instrumentStatisticsABC.getMinimum()) == 0, "Minimum");
		Assert.isTrue(Double.compare(1.05, instrumentStatisticsABC.getQuantile_5()) == 0, "Quantile");
		Assert.isTrue(Double.compare(timeExponential1, instrumentStatisticsABC.getTimeExponentiallyWeightedAverage()) == 0, "Time weighted average");
		Assert.isTrue(Double.compare(1.0, instrumentStatisticsABC.getTimeWeightedAverage()) == 0, "Time weighted average");
		Assert.isTrue(Double.compare(0.5, instrumentStatisticsABC.getVolatility()) == 0, "Volatility");
		Assert.isTrue(2L == instrumentStatisticsABC.getCount(), "Count");

		actualTimestamp += 2;
		incommingTick = new IncommingTick("ABC", 0.0, actualTimestamp);
		this.tickResponse.processTick(incommingTick);

		Double timeExponential2 = (Math.exp(-this.tickResponse.getLambdaExponentialDecay() * 1) * 1.0 + Math.exp(-this.tickResponse.getLambdaExponentialDecay() * 2) * 2.0) / (Math.exp(-this.tickResponse.getLambdaExponentialDecay() * 1) + Math.exp(-this.tickResponse.getLambdaExponentialDecay() * 2) + 1.0);
		Double timeAverage2 = (5.0) / (3.0);
		Double volatility = Math.sqrt(2.0 / 3.0);
		instrumentStatisticsABC = this.tickResponse.getTickStorageContainer().DeliverStatistics("ABC", actualTimestamp);
		Assert.isTrue(Double.compare(1.0, instrumentStatisticsABC.getAverage()) == 0, "Average");
		Assert.isTrue(Double.compare(2.0, instrumentStatisticsABC.getMaximalDrawdown()) == 0, "Maximal drawdown");
		Assert.isTrue(Double.compare(2.0, instrumentStatisticsABC.getMaximum()) == 0, "Maximum");
		Assert.isTrue(Double.compare(0.0, instrumentStatisticsABC.getMinimum()) == 0, "Minimum");
		Assert.isTrue(Double.compare(0.1, instrumentStatisticsABC.getQuantile_5()) == 0, "Quantile");
		Assert.isTrue(Double.compare(timeExponential2, instrumentStatisticsABC.getTimeExponentiallyWeightedAverage()) == 0, "Time weighted average");
		Assert.isTrue(Double.compare(timeAverage2, instrumentStatisticsABC.getTimeWeightedAverage()) == 0, "Time weighted average");
		Assert.isTrue(Double.compare(volatility, instrumentStatisticsABC.getVolatility()) == 0, "Volatility");
		Assert.isTrue(3L == instrumentStatisticsABC.getCount(), "Count");
	}

	@Test
	void timeMoveTest() {
		Long slideWindow = 60000L;
		this.tickResponse = new TickResponse(slideWindow, 1.0);

		InstrumentStatistics instrumentStatisticsABC = this.tickResponse.statistics("ABC");

		Long actualTimestamp = System.currentTimeMillis();
		IncommingTick incommingTick = new IncommingTick("ABC", 1.0, actualTimestamp);
		this.tickResponse.processTick(incommingTick);

		actualTimestamp += 1;
		incommingTick = new IncommingTick("ABC", 2.0, actualTimestamp);
		this.tickResponse.processTick(incommingTick);

		actualTimestamp += 1;
		incommingTick = new IncommingTick("ABC", 0.0, actualTimestamp);
		this.tickResponse.processTick(incommingTick);

		for (Long count = 0L; count < slideWindow - 1; count++) {
			Double volatility = Math.sqrt(2.0 / 3.0);
			// check of the
			instrumentStatisticsABC = this.tickResponse.getTickStorageContainer().DeliverStatistics("ABC", actualTimestamp + count);
			Assert.isTrue(Double.compare(1.0, instrumentStatisticsABC.getAverage()) == 0, "Average");
			Assert.isTrue(Double.compare(2.0, instrumentStatisticsABC.getMaximalDrawdown()) == 0, "Maximal drawdown");
			Assert.isTrue(Double.compare(2.0, instrumentStatisticsABC.getMaximum()) == 0, "Maximum");
			Assert.isTrue(Double.compare(0.0, instrumentStatisticsABC.getMinimum()) == 0, "Minimum");
			Assert.isTrue(Double.compare(0.1, instrumentStatisticsABC.getQuantile_5()) == 0, "Quantile");
			Assert.isTrue(Double.compare(volatility, instrumentStatisticsABC.getVolatility()) == 0, "Volatility");
			Assert.isTrue(3L == instrumentStatisticsABC.getCount(), "Count");
		}

		Double volatility = Math.sqrt(2.0 / 2.0);
		instrumentStatisticsABC = this.tickResponse.getTickStorageContainer().DeliverStatistics("ABC", actualTimestamp + slideWindow - 1);
		Assert.isTrue(Double.compare(1.0, instrumentStatisticsABC.getAverage()) == 0, "Average");
		Assert.isTrue(Double.compare(2.0, instrumentStatisticsABC.getMaximalDrawdown()) == 0, "Maximal drawdown");
		Assert.isTrue(Double.compare(2.0, instrumentStatisticsABC.getMaximum()) == 0, "Maximum");
		Assert.isTrue(Double.compare(0.0, instrumentStatisticsABC.getMinimum()) == 0, "Minimum");
		Assert.isTrue(Double.compare(0.1, instrumentStatisticsABC.getQuantile_5()) == 0, "Quantile");
		Assert.isTrue(Double.compare(volatility, instrumentStatisticsABC.getVolatility()) == 0, "Volatility");
		Assert.isTrue(2L == instrumentStatisticsABC.getCount(), "Count");

		instrumentStatisticsABC = this.tickResponse.getTickStorageContainer().DeliverStatistics("ABC", actualTimestamp + slideWindow);
		Assert.isTrue(Double.compare(0.0, instrumentStatisticsABC.getAverage()) == 0, "Average");
		Assert.isTrue(Double.compare(0.0, instrumentStatisticsABC.getMaximalDrawdown()) == 0, "Maximal drawdown");
		Assert.isTrue(Double.compare(0.0, instrumentStatisticsABC.getMaximum()) == 0, "Maximum");
		Assert.isTrue(Double.compare(0.0, instrumentStatisticsABC.getMinimum()) == 0, "Minimum");
		Assert.isTrue(Double.compare(0.0, instrumentStatisticsABC.getQuantile_5()) == 0, "Quantile");
		Assert.isTrue(Double.compare(0.0, instrumentStatisticsABC.getVolatility()) == 0, "Volatility");
		Assert.isTrue(1L == instrumentStatisticsABC.getCount(), "Count");

		instrumentStatisticsABC = this.tickResponse.getTickStorageContainer().DeliverStatistics("ABC", actualTimestamp + slideWindow + 1);
		Assert.isTrue(Double.compare(Double.NaN, instrumentStatisticsABC.getAverage()) == 0, "Average");
		Assert.isTrue(Double.compare(Double.NaN, instrumentStatisticsABC.getMaximalDrawdown()) == 0, "Maximal drawdown");
		Assert.isTrue(Double.compare(Double.NaN, instrumentStatisticsABC.getMaximum()) == 0, "Maximum");
		Assert.isTrue(Double.compare(Double.NaN, instrumentStatisticsABC.getMinimum()) == 0, "Minimum");
		Assert.isTrue(Double.compare(Double.NaN, instrumentStatisticsABC.getQuantile_5()) == 0, "Quantile");
		Assert.isTrue(Double.compare(Double.NaN, instrumentStatisticsABC.getVolatility()) == 0, "Volatility");
		Assert.isTrue(0L == instrumentStatisticsABC.getCount(), "Count");

	}
}
