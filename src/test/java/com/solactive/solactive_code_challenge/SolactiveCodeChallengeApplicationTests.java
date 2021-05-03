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
	public void testSetup()
	{
	}

	@Test
	void singleTest() {
		this.tickResponse = new TickResponse();
		this.tickResponse.setTimeHorizon(60000L);
		this.tickResponse.setLambdaExponentialDecay(1.0);

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

		instrumentStatisticsABC = this.tickResponse.statistics("ABC");
		Assert.isTrue(Double.compare(1.0, instrumentStatisticsABC.getAverage()) == 0, "Average");
		Assert.isTrue(Double.compare(0.0, instrumentStatisticsABC.getMaximalDrawdown()) == 0, "Maximal drawdown");
		Assert.isTrue(Double.compare(1.0, instrumentStatisticsABC.getMaximum()) == 0, "Maximum");
		Assert.isTrue(Double.compare(1.0, instrumentStatisticsABC.getMinimum()) == 0, "Minimum");
		Assert.isTrue(Double.compare(1.0, instrumentStatisticsABC.getQuantile_5()) == 0, "Quantile");
		Assert.isTrue(Double.compare(Double.NaN, instrumentStatisticsABC.getTimeExponentiallyWeightedAverage()) == 0, "Time weighted average");
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

		instrumentStatisticsABC = this.tickResponse.statistics("ABC");
		Assert.isTrue(Double.compare(1.5, instrumentStatisticsABC.getAverage()) == 0, "Average");
		Assert.isTrue(Double.compare(0.0, instrumentStatisticsABC.getMaximalDrawdown()) == 0, "Maximal drawdown");
		Assert.isTrue(Double.compare(2.0, instrumentStatisticsABC.getMaximum()) == 0, "Maximum");
		Assert.isTrue(Double.compare(1.0, instrumentStatisticsABC.getMinimum()) == 0, "Minimum");
		Assert.isTrue(Double.compare(1.05, instrumentStatisticsABC.getQuantile_5()) == 0, "Quantile");
		Assert.isTrue(Double.compare(1.0, instrumentStatisticsABC.getTimeExponentiallyWeightedAverage()) == 0, "Time weighted average");
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

		Double volatility = Math.sqrt(2.0/3.0);
		instrumentStatisticsABC = this.tickResponse.statistics("ABC");
		Assert.isTrue(Double.compare(1.0, instrumentStatisticsABC.getAverage()) == 0, "Average");
		Assert.isTrue(Double.compare(2.0, instrumentStatisticsABC.getMaximalDrawdown()) == 0, "Maximal drawdown");
		Assert.isTrue(Double.compare(2.0, instrumentStatisticsABC.getMaximum()) == 0, "Maximum");
		Assert.isTrue(Double.compare(0.0, instrumentStatisticsABC.getMinimum()) == 0, "Minimum");
		Assert.isTrue(Double.compare(1.1, instrumentStatisticsABC.getQuantile_5()) == 0, "Quantile");
		Assert.isTrue(Double.compare(1.5, instrumentStatisticsABC.getTimeExponentiallyWeightedAverage()) == 0, "Time weighted average");
		Assert.isTrue(Double.compare(1.5, instrumentStatisticsABC.getTimeWeightedAverage()) == 0, "Time weighted average");
		Assert.isTrue(Double.compare(volatility, instrumentStatisticsABC.getVolatility()) == 0, "Volatility");
		Assert.isTrue(3L == instrumentStatisticsABC.getCount(), "Count");

	}

	@Test
	void reverseOrderTest() {
		this.tickResponse = new TickResponse();
		this.tickResponse.setTimeHorizon(60000L);
		this.tickResponse.setLambdaExponentialDecay(1.0);

		InstrumentStatistics instrumentStatisticsABC = this.tickResponse.statistics("ABC");

		Long actualTimestamp = System.currentTimeMillis();
		IncommingTick incommingTick = new IncommingTick("ABC", 2.0, actualTimestamp);
		this.tickResponse.processTick(incommingTick);

		actualTimestamp -= 1;
		incommingTick = new IncommingTick("ABC", 1.0, actualTimestamp);
		this.tickResponse.processTick(incommingTick);

		instrumentStatisticsABC = this.tickResponse.statistics("ABC");
		Assert.isTrue(Double.compare(1.5, instrumentStatisticsABC.getAverage()) == 0, "Average");
		Assert.isTrue(Double.compare(0.0, instrumentStatisticsABC.getMaximalDrawdown()) == 0, "Maximal drawdown");
		Assert.isTrue(Double.compare(2.0, instrumentStatisticsABC.getMaximum()) == 0, "Maximum");
		Assert.isTrue(Double.compare(1.0, instrumentStatisticsABC.getMinimum()) == 0, "Minimum");
		Assert.isTrue(Double.compare(1.05, instrumentStatisticsABC.getQuantile_5()) == 0, "Quantile");
		Assert.isTrue(Double.compare(1.0, instrumentStatisticsABC.getTimeExponentiallyWeightedAverage()) == 0, "Time weighted average");
		Assert.isTrue(Double.compare(1.0, instrumentStatisticsABC.getTimeWeightedAverage()) == 0, "Time weighted average");
		Assert.isTrue(Double.compare(0.5, instrumentStatisticsABC.getVolatility()) == 0, "Volatility");
		Assert.isTrue(2L == instrumentStatisticsABC.getCount(), "Count");

	}

	@Test
	void timeManipulationTest() {

	}

}
