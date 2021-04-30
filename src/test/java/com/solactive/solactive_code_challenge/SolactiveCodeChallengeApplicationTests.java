package com.solactive.solactive_code_challenge;

import com.solactive.solactive_code_challenge.models.dtos.IncommingTick;
import com.solactive.solactive_code_challenge.models.dtos.InstrumentStatistics;
import com.solactive.solactive_code_challenge.resrapi.TickResponse;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import org.testng.annotations.BeforeClass;

@RunWith(SpringRunner.class )
@SpringBootTest
class SolactiveCodeChallengeApplicationTests {

	@Autowired
	TickResponse tickResponse;

	@BeforeClass
	public void testSetup()
	{
		this.tickResponse = new TickResponse();
	}

	@Test
	void singleTest() {
		InstrumentStatistics instrumentStatisticsABC = this.tickResponse.statistics("ABC");

		Assert.isTrue(Double.compare(Double.NaN, instrumentStatisticsABC.getAverage()) == 0, "Average");
		Assert.isTrue(Double.compare(Double.NaN, instrumentStatisticsABC.getMaximalDrawdown()) == 0, "Maximal drawdown");
		Assert.isTrue(Double.compare(Double.NaN, instrumentStatisticsABC.getMaximum()) == 0, "Maximum");
		Assert.isTrue(Double.compare(Double.NaN, instrumentStatisticsABC.getMinimum()) == 0, "Minimum");
		Assert.isTrue(Double.compare(Double.NaN, instrumentStatisticsABC.getQuantile_95()) == 0, "Quantile");
		Assert.isTrue(Double.compare(Double.NaN, instrumentStatisticsABC.getTimeExponentiallyWeightedAverage()) == 0, "Time weighted average");
		Assert.isTrue(Double.compare(Double.NaN, instrumentStatisticsABC.getTimeWeightedAverage()) == 0, "Time weighted average");
		Assert.isTrue(Double.compare(Double.NaN, instrumentStatisticsABC.getVolatility()) == 0, "Volatility");
		Assert.isTrue(0L == instrumentStatisticsABC.getCount(), "Count");

		IncommingTick incommingTick = new IncommingTick("ABC", 1.0, 1L);
		this.tickResponse.processTick(incommingTick);

		instrumentStatisticsABC = this.tickResponse.statistics("ABC");
		Assert.isTrue(Double.compare(1.0, instrumentStatisticsABC.getAverage()) == 0, "Average");
		Assert.isTrue(Double.compare(0.0, instrumentStatisticsABC.getMaximalDrawdown()) == 0, "Maximal drawdown");
		Assert.isTrue(Double.compare(1.0, instrumentStatisticsABC.getMaximum()) == 0, "Maximum");
		Assert.isTrue(Double.compare(1.0, instrumentStatisticsABC.getMinimum()) == 0, "Minimum");
		Assert.isTrue(Double.compare(1.0, instrumentStatisticsABC.getQuantile_95()) == 0, "Quantile");
		Assert.isTrue(Double.compare(1.0, instrumentStatisticsABC.getTimeExponentiallyWeightedAverage()) == 0, "Time weighted average");
		Assert.isTrue(Double.compare(1.0, instrumentStatisticsABC.getTimeWeightedAverage()) == 0, "Time weighted average");
		Assert.isTrue(Double.compare(Double.NaN, instrumentStatisticsABC.getVolatility()) == 0, "Volatility");
		Assert.isTrue(1L == instrumentStatisticsABC.getCount(), "Count");

		InstrumentStatistics instrumentStatisticsDEF = this.tickResponse.statistics("DEF");
		Assert.isTrue(Double.compare(Double.NaN, instrumentStatisticsDEF.getAverage()) == 0, "Average");
		Assert.isTrue(Double.compare(Double.NaN, instrumentStatisticsDEF.getMaximalDrawdown()) == 0, "Maximal drawdown");
		Assert.isTrue(Double.compare(Double.NaN, instrumentStatisticsDEF.getMaximum()) == 0, "Maximum");
		Assert.isTrue(Double.compare(Double.NaN, instrumentStatisticsDEF.getMinimum()) == 0, "Minimum");
		Assert.isTrue(Double.compare(Double.NaN, instrumentStatisticsDEF.getQuantile_95()) == 0, "Quantile");
		Assert.isTrue(Double.compare(Double.NaN, instrumentStatisticsDEF.getTimeExponentiallyWeightedAverage()) == 0, "Time weighted average");
		Assert.isTrue(Double.compare(Double.NaN, instrumentStatisticsDEF.getTimeWeightedAverage()) == 0, "Time weighted average");
		Assert.isTrue(Double.compare(Double.NaN, instrumentStatisticsDEF.getVolatility()) == 0, "Volatility");
		Assert.isTrue(0L == instrumentStatisticsDEF.getCount(), "Count");

	}

}
