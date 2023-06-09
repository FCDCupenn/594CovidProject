package edu.upenn.cit594.ui;

import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.AdditionalFeatureProcessor;
import edu.upenn.cit594.processor.CovidDataProcessor;
import edu.upenn.cit594.processor.PopulationDataProcessor;
import edu.upenn.cit594.processor.PropertyAnalyzer;
import edu.upenn.cit594.util.FileCreater;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Userinterface {
	
	private  PropertyAnalyzer propertyAnalyzer;
	private  CovidDataProcessor covidDataProcessor;
	private AdditionalFeatureProcessor additionFeatureProcessor;

	public Userinterface (PropertyAnalyzer propertyAnalyzer, CovidDataProcessor covidDataProcessor, AdditionalFeatureProcessor additionFeatureProcessor){
		this.propertyAnalyzer = propertyAnalyzer;
		this.covidDataProcessor = covidDataProcessor;
		this.additionFeatureProcessor = additionFeatureProcessor;

	}

	
	
	
	
	/**this method will return a list of options
	 * 
	 * @param filenames [covid_data, properties_data, population_data, log_file]
	 * @return
	 */
	public  List<Integer> getAvailableActionsOptions(Map<String, String> fileNames) {
		// assume all files are correct	
		// population data will have option 2
		// covid data will have 3 only when population data exist
		// property data will show 4, 5, and show 6 only when population data exist
		// show 7 when all three data exist
		List<Integer> options = new ArrayList<>();
		// all input would have option 0
		options.add(0);
		options.add(1);
		
		int count = 0;
		// construct a checkOptions pannel
		if (fileNames.containsKey(FileCreater.POPULATION)) {
			options.add(2);
			count++;
		}
		// when covid file and population file exist together
		if (fileNames.containsKey(FileCreater.COVID) && count == 1) {
			options.add(3);
			count++;
		}
		if (fileNames.containsKey(FileCreater.PROPERTIES)) {
			List<Integer> listOfNumbers = Arrays.asList(4, 5, 6);
			options.addAll(listOfNumbers);
			count++;
		}
		// when 3 files exists together
		if (count == 3) {
			options.add(7);
		}
		
		
		Collections.sort(options);
		return options;
		
	}
	
	/**
	 * this will print out the available actions
	 * @param filenames
	 */
	
	public  void printAvailableActionsOptions(Map<String, String> fileNames){
		Logger l = Logger.getInstance();
//		String[] availableActions = {"Exit the program",
//				"Show the available actions",
//				"Show the total population for all ZIP Codes",
//				"Show the total vaccinations per capita for each ZIP Code for the specified date",
//				"Show the average market value for properties in a specified ZIP Code",
//				"Show the average total livable area for properties in a specified ZIP Code",
//				"Show the total market value of properties, per capita, for a specified ZIP Code",
//				"Show the results of your custom feature"};
		List<Integer> options = new ArrayList<>();
		options = getAvailableActionsOptions(fileNames);
		for(Map.Entry<String, String> filename : fileNames.entrySet()) {
			l.log("filenames " + filename.getValue());
		}
		System.out.println("BEGIN OUTPUT");
		for (int i = 0; i < options.size(); i++) {	
			System.out.println(options.get(i));
			l.log(String.valueOf(options.get(i)));
		}
		System.out.println("END OUTPUT");
		
		
	}
	
	/**
	 * this will print out the available actions
	 * @param filenames
	 */
	
	public  void printManu(){
		String[] availableActions = {"Exit the program",
				"Show the available actions",
				"Show the total population for all ZIP Codes",
				"Show the total vaccinations per capita for each ZIP Code for the specified date",
				"Show the average market value for properties in a specified ZIP Code",
				"Show the average total livable area for properties in a specified ZIP Code",
				"Show the total market value of properties, per capita, for a specified ZIP Code",
				"Show the results of your custom feature"};
		for (int i = 0; i < availableActions.length; i++) {
			System.out.println(i + ". " + availableActions[i]);
		}
	}

	public void printAvgMarketValue(String input){
		Logger l = Logger.getInstance();
		int AvgMarketValue = propertyAnalyzer.getAverageMarketValue(input);
		System.out.println("BEGIN OUTPUT" +"\n" + AvgMarketValue +"\n" + "END OUTPUT");
		l.log(String.valueOf(AvgMarketValue));
	}

	public  void printAvgTotalLivableArea(String input) {
		Logger l = Logger.getInstance();
		int AvgLivableValue = propertyAnalyzer.getAverageLivableArea(input);
		System.out.println("BEGIN OUTPUT" + "\n" + AvgLivableValue + "\n" + "END OUTPUT");
		l.log(String.valueOf(AvgLivableValue));
	}

	public  void printValuePerCapita(String input){
		Logger l = Logger.getInstance();
		int AvgValuePerCapita = propertyAnalyzer.getATotalMarketValuePerCapita(input);
		System.out.println("BEGIN OUTPUT" +"\n" + AvgValuePerCapita +"\n" + "END OUTPUT");
		l.log(String.valueOf(AvgValuePerCapita));
	}
	
	/**
	 * This method will print out the total population 
	 * @param count
	 */
	public  void printTotalPopulationForAllZipCodes() {
		Logger l = Logger.getInstance();
		long count = PopulationDataProcessor.totalPoplulation(covidDataProcessor.populationList);
		System.out.println("BEGIN OUTPUT");
		System.out.println(count);
		System.out.println("END OUTPUT");
		l.log(String.valueOf(count));
		
	}
	
	
	/**
	 * This method will print out the total partial or full vaction per captia
	 * @param partialorFullPerCapita
	 */
	public void printTotalPartialOrFullVacPerCapita(String date, String partialOrFull) {
		Logger l = Logger.getInstance();
		Map<String, Double> partialorFullPerCapita = covidDataProcessor.getpartialOrFullVacPerCapita(date, partialOrFull);
		System.out.println("BEGIN OUTPUT");
		for (Map.Entry<String, Double> pfc : partialorFullPerCapita.entrySet()) {		
			System.out.print(pfc.getKey() + " ");
			System.out.println(pfc.getValue());
			l.log(pfc.getKey() + " " + pfc.getValue());
		}
		System.out.println("END OUTPUT");
	
		
	}
	

	
	// addtion feature
	
	public  void printAdditionalFeature (String zipCode) {
		Logger l = Logger.getInstance();
		Map<Double, Integer> TotalHospitalizedAndTotalMarketValuePerCapita 
		= additionFeatureProcessor.getTotalHospitalizedAndTotalMarketValuePerCapita(zipCode);
		System.out.println("BEGIN OUTPUT");
		System.out.println("Hositalized PerCapita  Total Market value Per Capita");
		for (Map.Entry<Double, Integer> pfc : TotalHospitalizedAndTotalMarketValuePerCapita.entrySet()) {
			System.out.print(pfc.getKey() + "                 ");
			System.out.println(pfc.getValue());
			l.log(pfc.getKey() + " " + pfc.getValue());
		}
		
		System.out.println("END OUTPUT");
	}
	
	
	public  boolean checkDateFormat(String date) {
		String regex = "(\\d\\d\\d\\d)-(\\d\\d)-(\\d\\d)";
		Pattern p1 = Pattern.compile(regex);
		Matcher m1 = p1.matcher(date);
		if (m1.find()) return true;
		return false;
		
	}
	
	
}
