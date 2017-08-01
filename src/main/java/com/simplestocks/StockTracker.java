package com.simplestocks;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class for calling stock calculation methods
 * @author marga
 *
 */
public class StockTracker {

	private Set<Stock> stockList;

	/**
	 * Default constructor initialises StockList
	 * 
	 */
	public StockTracker()
	{
		this.stockList = new HashSet<Stock>();

	}

	/**
	 * Constructor populates given list of stocks
	 * @param populated list of stocks for calculations
	 */
	public StockTracker(Set<Stock> stockList)
	{
		this.stockList = stockList;

	}


	/**
	 * Returns list of unique stocks 
	 * @return set of stocks 
	 */
	public Set<Stock> getStockList() {

		return stockList;
	}


	/**
	 * For given stock add an associated trade 
	 * @param stock stock to which trade should be recorded
	 * @param trade - trade to be added to list
	 */
	public void addTrade(Stock stock, Trade trade) throws StockException {

		if(stock != null && trade != null )
		{
			stock.addTrade(trade);
		}
		else {
			throw new StockException("Unable to add trade to stock");
		}

	}




	/**
	 * Returns the dividend yield for given stock calculated using price given	
	 * @param stock containing dividend calculation and fields required
	 * @param price for which to use in dividend calculation
	 * @return dividendYield
	 */
	public BigDecimal getDividendYield(Stock stock, BigDecimal price) throws StockException {


		return stock.calculateDividendYield(price);
	}


	/**
	 * Calculates the volume weighted price using trades occurring over last 
	 * given time.
	 * @param minutes from current time over which trades occurred 
	 * @return Volume Weighted Price of trades
	 */
	public BigDecimal calculateVolWeightedPriceForLastMinutes(Stock stock, int minutes) throws StockException {

		List<Trade> latestTrades = new ArrayList<Trade>();

		//Find trades within time frame
		if(stock != null && minutes>=0) {
			Instant currentTime = new Date().toInstant();
			Instant fromTime = currentTime.minusSeconds(minutes*60);

			latestTrades = stock.getTradesForTimeFrame(fromTime, currentTime);
		}
		else
		{
			throw new StockException("Invalid parameters for calculation");
		}
		
		//Use trades to calculated volume weighted price
		return calculateVolWeightedPrice(latestTrades);

	}


	/**
	 * Calculates the Volume Weighted Price for given list of trades
	 * @param trades
	 * @return Volume Weighted Price
	 */
	private BigDecimal calculateVolWeightedPrice(List<Trade> trades){


		//Now find all trades for given stock and calculate total volume and total price * volume

		BigDecimal totalVolume = BigDecimal.ZERO;
		BigDecimal totalPriceVolume  = BigDecimal.ZERO;
				
		for(Trade trade: trades) {

			BigDecimal price = trade.getPrice();
			BigDecimal quantity = trade.getQuantity();

			if(price != BigDecimal.ZERO && quantity != BigDecimal.ZERO) {

				totalPriceVolume = totalPriceVolume.add(
						(price.multiply(quantity).setScale(2, BigDecimal.ROUND_HALF_UP)));

				totalVolume = totalVolume.add(trade.getQuantity());
			}
		}
		

		BigDecimal volumeWeightedPrice = (totalPriceVolume.divide(totalVolume, BigDecimal.ROUND_HALF_UP));


		return volumeWeightedPrice;

	}

	/**
	 * Calculates the All Share Index for stocks within set
	 * @param stocks Map of stock symbols with prices
	 * @return AllShareIndex value
	 */
	public BigDecimal calculateAllShareIndex() throws StockException{


		List<BigDecimal> stockPrices = new ArrayList<BigDecimal>();

		//Get Market Price for each stock
		for(Stock stock:this.getStockList()) {


			BigDecimal price = stock.getMarketPrice();
			if(price != null && price != BigDecimal.ZERO) {
				stockPrices.add(price);
			}
			else {
				throw new StockException("Invalid price found");
			}

		}

		BigDecimal gMean = calculateGeometricMean(stockPrices);


		return gMean;
	}

	/**
	 * Calculates the Geometric Mean for given stocks
	 * @param stockPrices
	 * @return Geometric mean value
	 */
	private BigDecimal calculateGeometricMean(List<BigDecimal> stockPrices) {


		BigDecimal product = new BigDecimal(1);

		for(BigDecimal price: stockPrices) {	


			product = product.multiply(price).setScale(2, BigDecimal.ROUND_HALF_UP);
		}

		BigDecimal gMean = new BigDecimal(
				Math.pow(product.doubleValue(), 1.0/stockPrices.size())).setScale(2, BigDecimal.ROUND_HALF_UP);


		return gMean;
	}
}
