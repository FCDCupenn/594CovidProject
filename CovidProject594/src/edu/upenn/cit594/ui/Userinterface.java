package edu.upenn.cit594.ui;

import edu.upenn.cit594.processor.CovidDataProcessor;
import edu.upenn.cit594.processor.PropertyAnalyzer;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Userinterface {
	
	private  PropertyAnalyzer propertyAnalyzer;
	private  CovidDataProcessor covidDataProcessor;

	public Userinterface (PropertyAnalyzer propertyAnalyzer, CovidDataProcessor covidDataProcessor){
		this.propertyAnalyzer = propertyAnalyzer;
		this.covidDataProcessor = covidDataProcessor;

	}

	/**
	 * this method will check if all input files are valid
	 * @param filenames
	 * @return return true if the file is valid
	 */
	public static boolean checkValidFileNames(String[] filenames){
		
		String [] prefix = {"--covid=", "--properties=", "--population=", "--log="};
		// check format
		String regex = "^--(?<name>.+?)=(?<value>.+)$";
		Pattern p1 = Pattern.compile(regex);
		for (String s : filenames) {
			Matcher m1 = p1.matcher(s);
			// if it matches
			if (m1.find()) {
			
					// if the file exists
					// check the name argument
					for (int i = 0; i< prefix.length; i++) {
						if (s.startsWith(prefix[i])) {
							// if the file contain the prefix, check if this file exist
							
							File file = new File(s.substring(prefix[i].length()));
							
							if (file.exists()) {
								//need to check if there is a duplicate				
								// the index will always start at 0
								int index = s.substring(prefix[i].length()-1).indexOf(prefix[i]);
								// if there is a replicate, then index will not be -1
								// so it will return false
								if (index != -1) return false;
							}
							else 
								return false;
						}
					}

			}
			else {
				return false;
			}
		}
		return true;
	}
	
	
	
	/**this method will return a list of options
	 * 
	 * @param filenames [covid_data, properties_data, population_data, log_file]
	 * @return
	 */
	private static List<Integer> getAvailableActionsOptions(String[] filenames) {
		// assume all files are correct	
		// population data will have option 2
		// covid data will have 3, 7 only when population data exist
		// property data will show 4, 5, and show 6 only when population data exist
		List<Integer> options = new ArrayList<>();
		// all input would have option 0
		options.add(0);
		options.add(1);
		List<Boolean> checkOptions = new ArrayList<>();
		
		// construct a checkOptions pannel
		for (int i = 0; i < filenames.length; i ++) {
			// the length of the file is not empty, means there is a file
			if (filenames[i].length() != 0) {
				checkOptions.add(true);
			}
			else checkOptions.add(false);
		}
		// check if population data exist
		if (checkOptions.get(2)) {
			options.add(2);
			// if covid data exist
			if (checkOptions.get(0)) {
				options.add(3);
				options.add(7);
			}
			// if property data exist
			if (checkOptions.get(1)) {
				options.add(4);
				options.add(5);
				options.add(6);
			}
			Collections.sort(options);
			return options;
			
		}
		else {
			// only covid exist
			if (checkOptions.get(0)) {
				options.add(7);
			}
			// if property data exist
			if (checkOptions.get(1)) {
				options.add(4);
				options.add(5);

			}
		}
		Collections.sort(options);
		return options;
		
	}
	
	/**
	 * this will print out the available actions
	 * @param filenames
	 */
	
	public static void printAvailableActionsOptions(String[] filenames){
//		String[] availableActions = {"Exit the program",
//				"Show the available actions",
//				"Show the total population for all ZIP Codes",
//				"Show the total vaccinations per capita for each ZIP Code for the specified date",
//				"Show the average market value for properties in a specified ZIP Code",
//				"Show the average total livable area for properties in a specified ZIP Code",
//				"Show the total market value of properties, per capita, for a specified ZIP Code",
//				"Show the results of your custom feature"};
		List<Integer> options = new ArrayList<>();
		options = getAvailableActionsOptions(filenames);
		System.out.println("BEGIN OUTPUT");
		for (int i = 0; i < options.size(); i++) {
			System.out.println(options.get(i));
		}
		System.out.println("END OUTPUT");
		
	}
	
	/**
	 * this will print out the available actions
	 * @param filenames
	 */
	
	public static void printManu(){
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

	public String printAvgMarketValue(String input){
		int AvgMarketValue = propertyAnalyzer.getAverageMarketValue(input);
		System.out.println("The Average Market Value in Area " + input +" is: " + AvgMarketValue);
		return "The Average Market Value in Area " + input +" is: " + AvgMarketValue;
	}

	public  String printAvgTotalLivableArea(String input){
		int AvgLivableValue = propertyAnalyzer.getAverageLivableArea(input);
		System.out.println("The Average Total Livable Area in " + input +" is: " + AvgLivableValue);
		return "The Average Total Livable Area in " + input + " is: " + AvgLivableValue;
	}

	public  String printValuePerCapita(String input){
		int AvgValuePerCapita = propertyAnalyzer.getATotalMarketValuePerCapita(input);
		System.out.println("The Average Total Market Value Per Capita in " + input +" is: " + AvgValuePerCapita);
		return "The Average Total Market Value Per Capita in " + input +" is: " + AvgValuePerCapita;

	}
	
	/**
	 * This method will print out the total population 
	 * @param count
	 */
	public static void printTotalPopulationForAllZipCodes(long count) {
		System.out.println("BEGIN OUTPUT");
		System.out.println(count);
		System.out.println("END OUTPUT");
	}
	
	
	/**
	 * This method will print out the total partial or full vaction per captia
	 * @param partialorFullPerCapita
	 */
	public static void printTotalPartialOrFullVacPerCapita(Map<String, Double> partialorFullPerCapita) {
		System.out.println("BEGIN OUTPUT");
		for (Map.Entry<String, Double> pfc : partialorFullPerCapita.entrySet()) {
			System.out.print(pfc.getKey() + " ");
			System.out.println(pfc.getValue());
		}
		System.out.println("END OUTPUT");
	}
	
	/**
	 * This method will print out the total negative or vaccine per capita 
	 * @param partialorFullPerCapita
	 */
	public static void printTotalNegOrPosVacPerCapita(Map<String, Double> negOrPos) {
		System.out.println("BEGIN OUTPUT");
		for (Map.Entry<String, Double> pfc : negOrPos.entrySet()) {
			System.out.print(pfc.getKey() + " ");
			System.out.println(pfc.getValue());
		}
		System.out.println("END OUTPUT");
	}
	
	
	// addtion feature
	
	public static void printAdditionalFeature (Map<Double, Integer> TotalHospitalizedAndTotalMarketValuePerCapita) {
		System.out.println("BEGIN OUTPUT");
		System.out.println("Hositalized PerCapita  Total Market value Per Capita");
		for (Map.Entry<Double, Integer> pfc : TotalHospitalizedAndTotalMarketValuePerCapita.entrySet()) {
			System.out.print(pfc.getKey() + "                 ");
			System.out.println(pfc.getValue());
		}
		
		System.out.println("END OUTPUT");
	}
	
	
	public static boolean checkDateFormat(String date) {
		String regex = "(\\d\\d\\d\\d)-(\\d\\d)-(\\d\\d)";
		Pattern p1 = Pattern.compile(regex);
		Matcher m1 = p1.matcher(date);
		if (m1.find()) return true;
		return false;
		
	}
	
	
}
