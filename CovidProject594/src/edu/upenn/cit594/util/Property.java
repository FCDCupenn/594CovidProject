package edu.upenn.cit594.util;

public class Property {
	
	private double marketValue;
	private double totalLivableArea;
	private String zipCode;
	
	public Property (double marketValue, double totalLivableArea, String zipCode) {
		this.marketValue = marketValue;
		this.totalLivableArea = totalLivableArea;
		this.zipCode = zipCode;
	}

	public double getMarketValue() {
		return marketValue;
	}

	public double getTotalLivableArea() {
		return totalLivableArea;
	}

	public String getZipCode() {
		return zipCode;
	}
	
	
}

