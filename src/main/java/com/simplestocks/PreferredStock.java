package com.simplestocks;

import java.math.BigDecimal;

/**
 * PreferredStock class extends Stock
 * @author marga
 *
 */
public class PreferredStock extends Stock{

	private BigDecimal fixedDiv;

	/**
	 * Constructor to initialise Preferred stock
	 * @param symbol
	 * @param price
	 * @param lastDiv
	 * @param parValue
	 * @param fixedDiv
	 */
	public PreferredStock(String symbol,BigDecimal price, BigDecimal lastDiv,BigDecimal parValue, BigDecimal fixedDiv){

		super(symbol, price, lastDiv, parValue);

		this.fixedDiv = fixedDiv;
	}

	/**
	 * Calculates Dividend Yield for stock given a price
	 * @param price for dividend calculation
	 * @return dividend yield
	 */
	@Override
	public BigDecimal calculateDividendYield(BigDecimal price) throws StockException {

		BigDecimal div = new BigDecimal(0);

		if(price!=null && price!=BigDecimal.ZERO) {

			div = fixedDiv.multiply(this.getParValue()).divide(price,2, BigDecimal.ROUND_HALF_UP);
		}
		else
		{
			throw new StockException("Invalid price entered");
		}
		return div;
	}



}
