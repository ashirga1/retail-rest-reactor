package com.maverick.retail.object;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * POJO representation of {@link Product}.
 * 
 * @author Asad Shirgaonkar.
 */
@XmlRootElement
public class Product 
{
	private long productId;
	
	private String name;
	
	private CurrencyPrice currencyPrice;

	public long getProductId() 
	{
		return productId;
	}

	public void setProductId(long productId) 
	{
		this.productId = productId;
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public CurrencyPrice getCurrencyPrice() 
	{
		return currencyPrice;
	}

	public void setCurrencyPrice(CurrencyPrice currencyPrice) 
	{
		this.currencyPrice = currencyPrice;
	}	
}
