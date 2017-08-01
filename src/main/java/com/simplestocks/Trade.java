package com.simplestocks;


import java.math.BigDecimal;
import java.time.Instant;

/**
 * Trade Class - holds trade details
 * @author marga
 *
 */
public class Trade {
	
	private BigDecimal quantity;
	private BuySell buySell;
	private BigDecimal tradePrice;
	private Instant tradeTime;


	/**
	 * Enumeration class for Trade Direction - BUY or SELL
	 * @author marga
	 *
	 */
	public static enum BuySell{
		BUY,
		SELL
		
	};
	
	/**
	 * Constructor initialising trade
	 * @param price
	 * @param quantity
	 * @param bs
	 * @param tradeTime
	 */
	public Trade(BigDecimal price, BigDecimal quantity, BuySell bs, Instant  tradeTime) {
		
		this.quantity = quantity;
		this.tradePrice = price;
		this.buySell = bs;
		this.tradeTime = tradeTime;
		
	}

	
	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BuySell getBuySell() {
		return buySell;
	}

	public void setBuySell(BuySell buySell) {
		this.buySell = buySell;
	}

	public BigDecimal getPrice() {
		return tradePrice;
	}

	public void setPrice(BigDecimal price) {
				
			this.tradePrice = price;
	}

	public Instant getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(Instant tradeTime) {
		this.tradeTime = tradeTime;
	}

	
	
}
