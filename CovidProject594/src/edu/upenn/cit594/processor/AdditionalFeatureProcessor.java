package edu.upenn.cit594.processor;

import java.util.HashMap;
import java.util.Map;

public class AdditionalFeatureProcessor {
	
	CovidDataProcessor cdp;
	PropertyAnalyzer pa;
	
	public AdditionalFeatureProcessor(CovidDataProcessor cdp, PropertyAnalyzer pa) {
		this.cdp = cdp;
		this.pa = pa; 
	}
	
	// feature 3.7 
		/**
		 * This method will return the total hospitalzed people for given data set from all data set and with the total market value
		 * for this given zipcode
		 * @param zipCode
		 * @return return a map of hosptialzed and total market value per captita in a Map
		 */
		public Map<Double, Integer>  getTotalHospitalizedAndTotalMarketValuePerCapita (String zipCode){
			Map<Double, Integer> res = new HashMap<>();
			Double THPC = cdp.getTotalHospitalizedPerZipPerCapita(zipCode);
			int TMPC = pa.getATotalMarketValuePerCapita(zipCode);
			res.put(THPC, TMPC);
						
			return res;
		}

}
