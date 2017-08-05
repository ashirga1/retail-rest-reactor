package com.maverick.retail.object;

/**
 * POJO representation of  {@link CurrencyPrice}.
 * 
 * @author Asad Shirgaonkar
 */
public class CurrencyPrice 
{
	private double value;
	
	private String currency;

	public double getValue() 
	{
		return value;
	}

	public void setValue(final double value) 
	{
		this.value = value;
	}

	public String getCurrency() 
	{
		return currency;
	}

	public void setCurrency(String currency) 
	{
		this.currency = currency;
	}
}

