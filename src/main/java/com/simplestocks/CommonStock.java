package com.simplestocks;

import java.math.BigDecimal;

/**
 * Common Stock Class extends Stock
 * @author marga
 *
 */
public class CommonStock extends Stock{


	/**
	 * Constructor initialise Common stock
	 * @param symbol
	 * @param price
	 * @param lastDiv
	 * @param parValue
	 */
	public CommonStock(String symbol, BigDecimal price, BigDecimal lastDiv, BigDecimal parValue) {

		super(symbol,price, lastDiv,parValue);

	}

	/**
	 * Calculates Dividend yield for common stock for given price
	 * @return dividend yield
	 */
	@Override
	public BigDecimal calculateDividendYield(BigDecimal price)throws StockException {

		BigDecimal div = BigDecimal.ZERO;
		
		if(price!=null && price!=BigDecimal.ZERO) {

			div = this.getLastDividend().divide(price,2, BigDecimal.ROUND_HALF_UP);
		}
		else
		{
			throw new StockException("Invalid price entered");
		}

		return div.setScale(2, BigDecimal.ROUND_HALF_UP);
	}



}
