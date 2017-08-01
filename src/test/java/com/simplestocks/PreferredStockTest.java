package com.simplestocks;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.Instant;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PreferredStockTest {

	private Stock testPreferredStock1;
	private Trade testTrade1;
	private BigDecimal price;
	private BigDecimal quantity;
	private Instant time;
	private Trade.BuySell bsFlag;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {

		//Price = 2.22
		//LastDividend = 8
		//ParValue =100
		//FixedDiv = 0.02

		testPreferredStock1 = new PreferredStock("PRE", new BigDecimal(2.22),new BigDecimal(8), new BigDecimal(100), new BigDecimal(0.02));

		//Test Trade
		price = new BigDecimal(2.2).setScale(2, BigDecimal.ROUND_HALF_UP);
		quantity = new BigDecimal(40).setScale(2, BigDecimal.ROUND_HALF_UP);
		bsFlag = Trade.BuySell.BUY;
		time = Instant.now();
		testTrade1 = new Trade(price,quantity,bsFlag,time);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCalculateDividendYield() {

		try {


			BigDecimal preferredYield = testPreferredStock1.calculateDividendYield(new BigDecimal(3.85));

			assertEquals(new BigDecimal(0.52).setScale(2,BigDecimal.ROUND_HALF_UP),preferredYield);
		}
		catch(Exception e) {
			assert(false);
		}
	}

	@Test
	public void testCalculatePriceEarningsRatio() {

		try {

			BigDecimal ratio = testPreferredStock1.calculatePriceEarningsRatio(new BigDecimal(3.85));
			assertEquals(new BigDecimal(0.48).setScale(2,BigDecimal.ROUND_HALF_UP),ratio);
		}
		catch(StockException ex) {
			assertFalse(ex.getMessage(), false);
		}
	}

	@Test
	public void testAddTrade() {

		assertEquals(0, testPreferredStock1.getTrades().size());
		testPreferredStock1.addTrade(testTrade1);

		assertEquals(1, testPreferredStock1.getTrades().size());
	}

	@Test
	public void testEquals() {

		// New stock identical except for symbol
		Stock newStock = new PreferredStock("TEA", new BigDecimal(2.22),new BigDecimal(8), new BigDecimal(100), new BigDecimal(0.02));
		assertFalse(testPreferredStock1.equals(newStock));

		Stock matchingStock = new PreferredStock("TEA", new BigDecimal(2.22),new BigDecimal(8), new BigDecimal(100), new BigDecimal(0.02));

		assertTrue(newStock.equals(matchingStock));
	}

}
