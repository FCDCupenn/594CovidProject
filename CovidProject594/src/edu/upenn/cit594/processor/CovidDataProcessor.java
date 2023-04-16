package edu.upenn.cit594.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import edu.upenn.cit594.util.Covid;

public class CovidDataProcessor {
	
	// covid data set d
	public List<Covid> covidDataSet;
	// this HashMap contains the map for zipcode and population
	public HashMap<Long, Long> populationPerZipCode;
	
	//public HashMap<Long, Long> populationSumPerZipCodePerDate;
	
	
	
	public long totalNumberOfInfection;
	public long totalNumberBooster;
	public long totalNumberOfHospitalized;
	public long totalPopulation;
	
	
	public CovidDataProcessor () {
		covidDataSet = new ArrayList<>();
		populationPerZipCode = new HashMap<>();
	}
	
	
	public long totalVaccinationFromGivenZipCode (long zipCode) {
		// invalid input
		if (zipCode > 99999 || zipCode < 9999) return -1; 
		
		
		
		return totalNumberOfInfection;
	}
	
	
	
	public Map<Long, Double> partialOrFullVacPerCapita(String date, String partialOrFull) {
		// this map store zipcode and partial vaccine per captia
		Map<Long, Double> res = new HashMap<>();
		
		Map<Long, Long> totalPartialOrFullVacNumber = new HashMap<>();
		
		if (partialOrFull.equals("partial")) {
			totalPartialOrFullVacNumber = this.totalPartialVacPerZipCodePerDate(date);
			
		}
		else {
			totalPartialOrFullVacNumber = this.totalFullyVacPerZipCodePerDate(date);
		}
		
		for (int i = 0; i < covidDataSet.size(); i++) {
			// if the date doesn't match or ZipCode doesn't match, skip that line
			String zipcode = Long.toString(covidDataSet.get(i).getZipCode());
			if (!covidDataSet.get(i).getDate().equals(date) || zipcode.length() != 5) {
				continue;
			}
			// if populationPerZipCode contains that key
			long zip = covidDataSet.get(i).getZipCode();
			if (populationPerZipCode.containsKey(zip)) {	
				double partialPerCaptita = totalPartialOrFullVacNumber.get(zip) / populationPerZipCode.get(zip);
				res.put(zip, partialPerCaptita);
			}
			// doesn't contain this key, will put 0 in it
			else {
				res.put(zip, 0.000);
			}
			
		}
		
		
		return res;
			
	}
	
	/**
	 * This method will return the total number of vac calculation per zipcode for each given date
	 * @return
	 */
	
	public Map<Long, Long> totalPartialVacPerZipCodePerDate(String date) {
		// this map will store the zipcode for each sum
		Map<Long, Long> res = new HashMap<>();
		for (int i = 0; i < covidDataSet.size(); i++) {
			// if the date doesn't match or ZipCode doesn't match, skip that line
			String zipcode = Long.toString(covidDataSet.get(i).getZipCode());
			if (!covidDataSet.get(i).getDate().equals(date) || zipcode.length() != 5) {
				continue;
			}
			
			// calculate the sum for each zip and date
			if (!res.containsKey(covidDataSet.get(i).getZipCode())) {
				res.put(covidDataSet.get(i).getZipCode(), covidDataSet.get(i).getPartialVaccinated());
			}
			else
				res.put(covidDataSet.get(i).getZipCode(), 
						covidDataSet.get(i).getPartialVaccinated() + res.get(covidDataSet.get(i).getZipCode()));
			
		}
		
		return res;
	}
	
	public Map<Long, Long> totalFullyVacPerZipCodePerDate(String date) {
		// this map will store the zipcode for each sum
		Map<Long, Long> res = new HashMap<>();
		for (int i = 0; i < covidDataSet.size(); i++) {
			// if the date doesn't match or ZipCode doesn't match, skip that line
			String zipcode = Long.toString(covidDataSet.get(i).getZipCode());
			if (!covidDataSet.get(i).getDate().equals(date) || zipcode.length() != 5) {
				continue;
			}
			
			// calculate the sum for each zip and date
			if (!res.containsKey(covidDataSet.get(i).getZipCode())) {
				res.put(covidDataSet.get(i).getZipCode(), covidDataSet.get(i).getFullyVaccinated());
			}
			else
				res.put(covidDataSet.get(i).getZipCode(), 
						covidDataSet.get(i).getFullyVaccinated() + res.get(covidDataSet.get(i).getZipCode()));
			
		}
		
		return res;
	}
	
	public Map<Long, Long> totalNegVacPerZipCodePerDate(String date) {
		// this map will store the zipcode for each sum
		Map<Long, Long> res = new HashMap<>();
		for (int i = 0; i < covidDataSet.size(); i++) {
			// if the date doesn't match or ZipCode doesn't match, skip that line
			String zipcode = Long.toString(covidDataSet.get(i).getZipCode());
			if (!covidDataSet.get(i).getDate().equals(date) || zipcode.length() != 5) {
				continue;
			}
			
			// calculate the sum for each zip and date
			if (!res.containsKey(covidDataSet.get(i).getZipCode())) {
				res.put(covidDataSet.get(i).getZipCode(), covidDataSet.get(i).getNegTest());
			}
			else
				res.put(covidDataSet.get(i).getZipCode(), 
						covidDataSet.get(i).getNegTest() + res.get(covidDataSet.get(i).getZipCode()));
			
		}
		
		return res;
	}
	
	public Map<Long, Long> totalPosVacPerZipCodePerDate(String date) {
		// this map will store the zipcode for each sum
		//HashMap<Long, Long> res = new HashMap<>();
		 Map<Long, Long> res = covidDataSet.stream().filter(e -> e.getDate().equals(date)).
					collect(Collectors.groupingBy(Covid::getZipCode, Collectors.summingLong(Covid::getPosTest)));
		
		return res;
	}
	
	
}
