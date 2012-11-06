package edu.gatech.oftentimes2000.data;

public class Price 
{
	/**
	 * The original price
	 */
	public float originalPrice;
	
	/**
	 * The promotional price
	 */
	public float promotionalPrice;
	
	/**
	 * The discount in percentaage
	 */
	public int discount;
	
	/**
	 * Calculate the discount
	 */
	public void calcDiscount()
	{
		this.discount = (int) ((this.originalPrice - this.promotionalPrice) / this.originalPrice * 100);
	}
	
	/**
	 * Get the original price.
	 * @return the original price
	 */
	public String getOriginalPrice()
	{
		return String.format("%.2f", this.originalPrice);
	}
	
	/**
	 * Get the promotional price.
	 * @return the promotional price
	 */
	public String getPromotionalPrice()
	{
		return String.format("%.2f", this.promotionalPrice);
	}
	
	/**
	 * Get the discount.
	 * @return the discount
	 */
	public String getDiscount()
	{
		return this.discount + "%";
	}
}
