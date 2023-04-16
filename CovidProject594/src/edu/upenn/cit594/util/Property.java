package edu.upenn.cit594.util;

public class Property {
	
	private int marketValue;
	private int totalLivableArea;
	private int zipCode;
	
	public Property (int marketValue, int totalLivableArea, int zipCode) {
		this.marketValue = marketValue;
		this.totalLivableArea = totalLivableArea;
		this.zipCode = zipCode;
	}

	public int getMarketValue() {
		return marketValue;
	}

	public int getTotalLivableArea() {
		return totalLivableArea;
	}

	public int getZipCode() {
		return zipCode;
	}
	
	
}

