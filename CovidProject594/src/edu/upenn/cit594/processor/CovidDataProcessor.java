package edu.upenn.cit594.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import edu.upenn.cit594.datamanagement.AlmightyReader;
import edu.upenn.cit594.util.Covid;
import edu.upenn.cit594.util.Population;

public class CovidDataProcessor {
	
	// covid data set 
	public List<Covid> covidDataSet;
	
	// this is population list
	public List<Population> populationList;
	
	// this HashMap contains the map for zipcode and population for that zipcode
	public Map<String, Long> populationMap = new HashMap<>();
	
	public AlmightyReader reader;
	
	//public Map<String, Double> partialOrFullVacPerCapita = new TreeMap<>();

	
	
	public CovidDataProcessor (AlmightyReader reader) {
		this.reader = reader;
		covidDataSet = reader.getCovidList();
		populationList = reader.getPopulationList();
		populationMap = reader.populationListToMap(populationList);
	}
	
	
	
	/**
	 * This method will return the partial or full vaccination data per capita
	 * @param date
	 * @param partialOrFull
	 * @return return a Map of partial or full vaccination data
	 */
	
	public Map<String, Double> getpartialOrFullVacPerCapita(String date, String partialOrFull) {
		// this map store zipcode and partial vaccine per captia
		Map<String, Double> res = new TreeMap<>();
		
		Map<String, Long> totalPartialOrFullVacNumber = new HashMap<>();
		
		// get partial or full vaccination population per zipcode for each date
		if (partialOrFull.equals("partial")) {
			totalPartialOrFullVacNumber = this.getTotalPartialVacPerZipCodePerDate(date);
			
		}
		else {
			totalPartialOrFullVacNumber = this.getTotalFullyVacPerZipCodePerDate(date);
		}
		
		for (Map.Entry<String, Long> partialorFull : totalPartialOrFullVacNumber.entrySet()) {
			// if the date doesn't match or ZipCode doesn't match, skip that line
			String zipcode = partialorFull.getKey();
			// if populationPerZipCode contains that key
			if (populationMap.containsKey(zipcode)) {	
				
				long numerator = totalPartialOrFullVacNumber.get(zipcode);
				long denominator = populationMap.get(zipcode);
				
				double partialorFullPerCaptita = (double) numerator / denominator;
				
				partialorFullPerCaptita = (double) Math.round(partialorFullPerCaptita * 10000) / 10000;
				
				res.put(zipcode, partialorFullPerCaptita);
			}
			// doesn't contain this key, will put 0 in it
			else {
				res.put(zipcode, 0.0000);
			}
			
		}
		

		return res;
			
	}
	
	/**
	 * This method will return the partial number of vaccination calculation per zipcode for each given date
	 * @return a map containing the total number of partial vaccination data per zipcode
	 */
	
	public Map<String, Long> getTotalPartialVacPerZipCodePerDate(String date) {
		// this map will store the zipcode for each sum
		Map<String, Long> res = new HashMap<>();
		for (int i = 0; i < covidDataSet.size(); i++) {
			// if the date doesn't match or ZipCode doesn't match, skip that line, or the zipcode is empty
			String zipcode = covidDataSet.get(i).getZipCode();
			if (!covidDataSet.get(i).getDate().startsWith(date) || zipcode.isEmpty()) {
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
	
	/**
	 * This method will return the full number of vaccination calculation per zipcode for each given date
	 * @return a map containing the total number of full vaccination data per zipcode
	 */
	public Map<String, Long> getTotalFullyVacPerZipCodePerDate(String date) {
		// this map will store the zipcode for each sum
		Map<String, Long> res = new HashMap<>();
		for (int i = 0; i < covidDataSet.size(); i++) {
			// if the date doesn't match or ZipCode doesn't match, skip that line
			String zipcode = covidDataSet.get(i).getZipCode();
			if (!covidDataSet.get(i).getDate().startsWith(date) || zipcode.isEmpty()) {
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
	

	/**
	 * this number will return the positive tested for a specific zip code
	 * @param date
	 * @return a Map that contains the positive zipcode
	 */
	public Map<String, Long> totalPosVacPerZipCodePerDate(String date) {
		// this map will store the zipcode for each sum
		//HashMap<Long, Long> res = new HashMap<>();
		 Map<String, Long> res = covidDataSet.stream().filter(e -> e.getDate().startsWith(date)).
					collect(Collectors.groupingBy(Covid::getZipCode, Collectors.summingLong(Covid::getPosTest)));
		
		return res;
	}
	
	/**
	 * this number will return the negative tested for a specific zip code
	 * @param date
	 * @return a Map that contains the positive zipcode
	 */
	public Map<String, Long> totalNegVacPerZipCodePerDate(String date) {
		// this map will store the zipcode for each sum
		//HashMap<Long, Long> res = new HashMap<>();
		 Map<String, Long> res = covidDataSet.stream().filter(e -> e.getDate().startsWith(date)).
					collect(Collectors.groupingBy(Covid::getZipCode, Collectors.summingLong(Covid::getNegTest)));
		
		return res;
	}
	
	/** This method will return the total positive vac per Date per capta for postive vaccine for option 7
	 * 
	 * @param date
	 * @param negOrPos
	 * @return
	 */
	public Map<String, Double> getTotalNegOrPosVacPerZipCodePerDatePerCapita(String date, String negOrPos){
		Map<String, Double> res = new TreeMap<>();
		
		Map<String, Long> totalNegorPos = new HashMap<>();
		
		// get negative or positive vaccination population per zipcode for each date
		if (negOrPos.equals("negative")) {
			totalNegorPos = this.totalNegVacPerZipCodePerDate(date);
			
		}
		else {
			totalNegorPos = this.totalPosVacPerZipCodePerDate(date);
		}
		
		for (Map.Entry<String, Long> partialorFull : totalNegorPos.entrySet()) {
			// if the date doesn't match or ZipCode doesn't match, skip that line
			String zipcode = partialorFull.getKey();
			// if populationPerZipCode contains that key
			if (populationMap.containsKey(zipcode)) {	
				
				long numerator = totalNegorPos.get(zipcode);
				long denominator = populationMap.get(zipcode);
				
				double partialorFullPerCaptita = (double) numerator / denominator;
				
				partialorFullPerCaptita = (double) Math.round(partialorFullPerCaptita * 10000) / 10000;
				
				res.put(zipcode, partialorFullPerCaptita);
			}
			// doesn't contain this key, will put 0 in it
			else {
				res.put(zipcode, 0.0000);
			}
			
		}
		
		
		return res;
	}
	
	
	/**
	 * This method will the total hopstialized people per zip per capta for the whole time
	 * @param zipCode
	 * @return a double that represent the capita 
	 */
	public double getTotalHospitalizedPerZipPerCapita(String zipCode){
		if (!populationMap.containsKey(zipCode)) {
			return 0.0000;
		}
		int count = 0;
		// get the total hospitalized people
		for (int i = 0; i < covidDataSet.size(); i++) {
			if (covidDataSet.get(i).getZipCode().equals(zipCode)) {
				count += covidDataSet.get(i).getHospitalization();
			}
		}
		int numerator = count;
		Long denominator = populationMap.get(zipCode);
		Double countPerCapita = (double)numerator / denominator;
		
		countPerCapita = (double) Math.round(countPerCapita * 10000) / 10000;
		
		return countPerCapita;
		
	}
}
