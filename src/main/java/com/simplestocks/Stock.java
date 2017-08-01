package com.simplestocks;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Stock Base Class
 * @author marga
 *
 *
 */
public abstract class Stock {

	private String symbol;
	private BigDecimal lastDiv;
	private BigDecimal parValue;
	private BigDecimal marketPrice;
	private List<Trade> trades;


	/**
	 * Constructor to initialize stock
	 * @param symbol
	 * @param price
	 * @param lastDiv
	 * @param parValue
	 */
	protected Stock(String symbol,BigDecimal price, BigDecimal lastDiv,BigDecimal parValue) {

		this.symbol = symbol;
		this.lastDiv = lastDiv;
		this.parValue = parValue;
		this.marketPrice = price;

		//create new list of trades for this stock;
		this.trades = new ArrayList<Trade>();

	}


	/**
	 * Abstract method to calculate Dividend Yield for this stock
	 * @param price used to calculate dividend yield
	 * @return Dividend Yield value
	 */
	public abstract BigDecimal calculateDividendYield(BigDecimal price) throws StockException;

	/**
	 * Calculate P/E ratio for this stock
	 * @param price to use for P/E calculation
	 * @return ratio value;
	 */
	public BigDecimal calculatePriceEarningsRatio(BigDecimal price) throws StockException{

		BigDecimal ratio = BigDecimal.ZERO;



		if(price!=null && price!=BigDecimal.ZERO && this.getLastDividend() != BigDecimal.ZERO)
		{
			ratio = price.divide(lastDiv,2, BigDecimal.ROUND_HALF_UP);

		}
		else {
			throw new StockException("Invalid price for stock entered");
		}

		return ratio;

	}

	/**
	 * Add a trade to this stocks list of trades
	 * @param trade
	 */
	public void addTrade(Trade trade) {
		this.trades.add(trade);
	}


	/**
	 * Returns market price for stock
	 * @return marketPrice for stock
	 */
	public BigDecimal getMarketPrice() {
		return marketPrice;
	}


	/**
	 * Sets market price for stock
	 * @param marketPrice
	 */
	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}

	/**
	 * Returns list of trades for this stock
	 * @return list of trades
	 */
	public List<Trade> getTrades() {
		return trades;
	}

	/**
	 * Return last dividend value
	 * @return lastDiv
	 */
	public BigDecimal getLastDividend() {
		return lastDiv;
	}

	/**
	 * Set last dividend value for stock
	 * @param lastDiv
	 */
	public void setLastDiv(BigDecimal lastDiv) {
		this.lastDiv = lastDiv;
	}

	/**
	 * Return par value for this stock
	 * @return par value
	 */
	protected BigDecimal getParValue() {
		return parValue;
	}

	/**
	 * Set Par value for this stock
	 * @param parValue
	 */
	protected void setParValue(BigDecimal parValue) {
		this.parValue = parValue;
	}

	/**
	 * Set stock symbol
	 * @param symbol
	 */
	protected void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * Return symbol for this stock
	 * @return symbol
	 */
	protected String getSymbol() {

		return this.symbol;
	}


	/**
	 * Returns all trades between given times
	 * @param fromTime Instant representation of time to start search from
	 * @param toTime Instant representation of time to stop search
	 * @return List of trades that match given time-frame
	 */
	public List<Trade> getTradesForTimeFrame(Instant fromTime, Instant toTime) throws StockException{


		List<Trade> timedTrades = new ArrayList<Trade>();

		if(fromTime != null && toTime != null)
		{
		for(Trade trade:this.getTrades()) {


			if(trade.getTradeTime().isAfter(fromTime) && trade.getTradeTime().isBefore(toTime))
			{
				timedTrades.add(trade);
			}
		}
		}
		else {
			throw new StockException("Invalid time entered");
		}
		
		return timedTrades;
	}

	/**
	 * Compare Stocks by checking stock symbols are equal
	 * @return boolean true if matching stock symbols
	 */
	public boolean equals(Object obj) {

		boolean equals = false;
		if((obj != null) && (obj instanceof Stock)) {

			Stock stockToCompare = (Stock) obj;

			if(this.getSymbol().equals(stockToCompare.getSymbol())) 
			{
				equals = true;
			}	 

		}
		return equals;
	}

}
