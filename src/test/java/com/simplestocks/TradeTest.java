package com.simplestocks;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.Instant;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TradeTest {

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
	public void testTrade() {
		
		//getPrice
		assertEquals(price, testTrade1.getPrice());
		
		//getQuantity
		assertEquals(quantity, testTrade1.getQuantity());
		
		//getTime
		assertEquals(time, testTrade1.getTradeTime());
		
		//getBuySell
		assertEquals(Trade.BuySell.BUY, testTrade1.getBuySell());
		
	
	}

}
