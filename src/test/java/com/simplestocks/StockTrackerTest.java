package com.simplestocks;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test class for StockTracker methods
 * @author marga
 *
 */
public class StockTrackerTest {

	private Stock testCommonStock1;
	private Stock testPreferredStock1;

	private Trade testTrade1;
	private Trade testTrade2;
	private Trade testTrade3;

	private BigDecimal price;
	private BigDecimal quantity;
	private Instant time;

	Set<Stock> sampleStocks;

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

		testCommonStock1 = new CommonStock("COM", new BigDecimal(2.22), new BigDecimal(8),new BigDecimal(100));
		testPreferredStock1 = new PreferredStock("PRE", new BigDecimal(2.22),new BigDecimal(8), new BigDecimal(100), new BigDecimal(0.02));

		sampleStocks = new HashSet<Stock>();
		sampleStocks.add(new CommonStock("TEA",new BigDecimal(2.2), new BigDecimal(0),new BigDecimal(100)));
		sampleStocks.add(new CommonStock("POP",new BigDecimal(6.2), new BigDecimal(8),new BigDecimal(100)));
		sampleStocks.add(new CommonStock("ALE",new BigDecimal(4.5), new BigDecimal(23),new BigDecimal(60)));
		sampleStocks.add(new PreferredStock("GIN",new BigDecimal(2.2), new BigDecimal(8),new BigDecimal(100),new BigDecimal(2)));
		sampleStocks.add(new CommonStock("JOE",new BigDecimal(3.3), new BigDecimal(13),new BigDecimal(250)));

		price = new BigDecimal(2.2).setScale(2, BigDecimal.ROUND_HALF_UP);
		quantity = new BigDecimal(40).setScale(2, BigDecimal.ROUND_HALF_UP);
		time = Instant.now().minusSeconds(30);


		testTrade1 = new Trade(price,quantity,Trade.BuySell.BUY,time);


	}

	@After
	public void tearDown() throws Exception {
	}


	@Test
	public void testAddTrade() {

		try {

			StockTracker tracker = new StockTracker();
			assertEquals(0, testCommonStock1.getTrades().size());

			tracker.addTrade(testCommonStock1, testTrade1);
			assertEquals(1, testCommonStock1.getTrades().size());	
		}
		catch(Exception ex) {
			assert(false);
		}
	}


	@Test
	public void testCalculateAllShareIndex() {

		StockTracker tracker = new StockTracker(sampleStocks);

		try {
			BigDecimal index = tracker.calculateAllShareIndex();

			// Calculation based on product of prices of 445.62
			BigDecimal expected = new BigDecimal(3.39).setScale(2, BigDecimal.ROUND_HALF_UP);

			assertEquals(expected, index);

		}catch(Exception ex)
		{
			//no exception expected
			assert(false);	
		}

		CommonStock zeroPrice = new CommonStock("COM", new BigDecimal(0), new BigDecimal(0),new BigDecimal(0));
		CommonStock nullPrice = new CommonStock("COM", null, new BigDecimal(0),new BigDecimal(0));
		CommonStock negativePrice = new CommonStock("COM", new BigDecimal(-2.0), new BigDecimal(0),new BigDecimal(0));

		//test price of 0 and negative values
		sampleStocks.add(negativePrice);
		sampleStocks.add(nullPrice);


		StockTracker tracker2 = new StockTracker(sampleStocks);
		boolean error = false;
		try {
			
			//exception should be thrown for null price
			tracker2.calculateAllShareIndex();
		}
		catch(Exception ex) {
			
			error = true;
		}

		sampleStocks.add(zeroPrice);
		StockTracker tracker3 = new StockTracker(sampleStocks);

		try {
			//exception should be thrown for negative price
			tracker3.calculateAllShareIndex();
		}
		catch(Exception ex){
			error = true;
		}
		
		assert(error);
	}


	@Test
	public void testCalculateVolWeightedPriceForTime() {


		StockTracker tracker = new StockTracker(sampleStocks);

		BigDecimal newPrice = new BigDecimal(3.5).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal newQuantity = new BigDecimal(10).setScale(2, BigDecimal.ROUND_HALF_UP);

		//15 mins = 900secs
		testTrade2 = new Trade(newPrice,newQuantity,Trade.BuySell.BUY,time.minusSeconds(300));
		testTrade3 = new Trade(newPrice,newQuantity,Trade.BuySell.SELL,time.minusSeconds(915)); 

		Stock stock = sampleStocks.iterator().next();
		stock.addTrade(testTrade1);
		stock.addTrade(testTrade2);
		stock.addTrade(testTrade3);

		try {
			BigDecimal vwPrice = tracker.calculateVolWeightedPriceForLastMinutes(stock,15);
			BigDecimal expected = new BigDecimal(2.46).setScale(2, BigDecimal.ROUND_HALF_UP);

			assertEquals(expected, vwPrice);
		}
		catch(Exception ex) {
			assert(false);
		}
	}

	/**
	 * Test dividend yield calculation for different stock types
	 */
	@Test
	public void testGetDividendYield() {

		try {
			BigDecimal commonYield = new StockTracker().getDividendYield(testCommonStock1,new BigDecimal(3.85)); 
			BigDecimal preferredYield = new StockTracker().getDividendYield(testPreferredStock1,new BigDecimal(3.85));


			assertEquals(new BigDecimal(2.08).setScale(2,BigDecimal.ROUND_HALF_UP),commonYield);
			assertEquals(new BigDecimal(0.52).setScale(2,BigDecimal.ROUND_HALF_UP),preferredYield);


		}catch(Exception ex) {
			assert(false);
		}
	}
}
