package edu.upenn.cit594;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import edu.upenn.cit594.datamanagement.AlmightyReader;
import edu.upenn.cit594.datamanagement.CSVCovidReader;
import edu.upenn.cit594.datamanagement.JSONFileReader;
import edu.upenn.cit594.datamanagement.PopulationReader;
import edu.upenn.cit594.datamanagement.PropertyReader;
import edu.upenn.cit594.logging.Logger;
import edu.upenn.cit594.processor.AdditionalFeatureProcessor;
import edu.upenn.cit594.processor.CovidDataProcessor;
import edu.upenn.cit594.processor.PopulationDataProcessor;
import edu.upenn.cit594.processor.PropertyAnalyzer;
import edu.upenn.cit594.ui.Userinterface;
import edu.upenn.cit594.util.Covid;
import edu.upenn.cit594.util.FileCreater;
import edu.upenn.cit594.util.Population;
import edu.upenn.cit594.util.Property;

public class Main {

	public static void main(String[] args) throws IOException {
		
		if (args.length < 1 || args.length > 4 ) {
	          System.err.println("incorrect number of arguments");
	          return;
	       }	
		
		long startTime = System.currentTimeMillis();
		
		String[] files = args;
		// check if all file names are correct 
		if (!FileCreater.checkValidFileNames(files)) {
			System.out.println("file names are not valid");
			return;
		}
		// get final map for fileNames
		Map<String, String> fileNames = FileCreater.createFilesNames(files);
		Logger logger = Logger.getInstance();;
		logger.setLogFile(fileNames.get(FileCreater.LOG));

		// intial reader
		AlmightyReader reader_final  = new AlmightyReader(fileNames);
		CovidDataProcessor cdp_final = new CovidDataProcessor(reader_final);
		PropertyAnalyzer property_csv = new PropertyAnalyzer(reader_final);
		AdditionalFeatureProcessor afp = new AdditionalFeatureProcessor(cdp_final, property_csv);
		Userinterface ui = new Userinterface(property_csv, cdp_final, afp);
		
		
		
		


		Scanner scanner = new Scanner(System.in);
		int choice = 0;
		boolean choiceCheck = false;
		List<Integer> userOptions = ui.getAvailableActionsOptions(fileNames);
		String input = null;
		String date = null; 
		
		
		// display the inital input
		ui.printManu();
		// input the a number
		choice = scanner.nextInt();
		// option check 
		while (!choiceCheck) {
			if (choice > 8) {
				System.out.println("You have to enter a number between 0 - 8");	
				choice = scanner.nextInt();
				continue;
				}
			
			boolean casesCheck = false;
			while (!casesCheck) {
				// check if the input is within the avalible options
				if (userOptions.contains(choice)) {
					casesCheck = true;
				}
				else {
					System.out.println("please enter the number within the availabe options");
					choice = scanner.nextInt();
				}
			}
			choiceCheck = true;
		}
		
		while (choice < 8) {
			// check options while in the loop
			
			switch (choice) {
			case 0: System.out.println("Exit!"); return;
			case 1:
				logger.log(" User input: " + choice);
				ui.printAvailableActionsOptions(fileNames);
				break;			
			case 2:
				logger.log("opened file: " + fileNames.get(FileCreater.POPULATION) + "User input: " + choice);
				ui.printTotalPopulationForAllZipCodes();
				break;
			case 3:
				logger.log(" User input: " + choice);
				System.out.println("Type partial or full");
				input = scanner.next();
				if (input.equals("partial")) {
					System.out.println("please enter a date in the format: YYYY-MM-DD");
					date = scanner.next();
					
					if (ui.checkDateFormat(date)) {
						logger.log("opened file: " + fileNames.get(FileCreater.COVID) + " User input: " + date + " " + input);
						ui.printTotalPartialOrFullVacPerCapita(date, "partial");
						
					}
					else {
						System.out.println("The date format is wrong, please enter a date in the format: YYYY-MM-DD");
						break;
					}

				}
				else if (input.equals("full")) {
					System.out.println("please enter a date in the format: YYYY-MM-DD");
					date = scanner.next();
					if (ui.checkDateFormat(date)) {
						logger.log("opened file: " + fileNames.get(FileCreater.COVID) + " User input: " + date + " " + input);
						ui.printTotalPartialOrFullVacPerCapita(date, "full");
					}
					else {	
						System.out.println("The date format is wrong, please enter a date in the format: YYYY-MM-DD");
						break;
					}

				}
				else
					System.out.println("Please Type partial or full");
					break;

			case 4:
				logger.log(" User input: " + choice);
				System.out.println("Please Type zipcode");
				input = scanner.next();
				if(input!=null){
					logger.log("opened file: " + fileNames.get(FileCreater.PROPERTIES) + " User input: " + input + " result: " + ui.printAvgMarketValue(input));
					
					break;
					}
				else{
					break;}

				case 5:
					logger.log(" User input: " + choice);
					System.out.println("Please Type zipcode");
					input = scanner.next();
					if(input!=null){
					logger.log("opened file: " + fileNames.get(FileCreater.PROPERTIES) + fileNames.get(FileCreater.POPULATION) + " User input: " + input + " result: " + ui.printAvgTotalLivableArea(input));
					break;}
					else{
						break;}


				case 6:
					logger.log(" User input: " + choice);
					System.out.println("Please Type zipcode");
					input = scanner.next();
					if(input!=null){
						logger.log("opened file: " + fileNames.get(FileCreater.PROPERTIES) + " User input: " + input + " result: " + ui.printValuePerCapita(input));
						break;}
					else{
						break;}

			case 7: 
				logger.log(" User input: " + choice);
				System.out.println("Please Type zipcode");
				input = scanner.next();
				if(input!=null){
					logger.log("opened file: " +fileNames.get(FileCreater.COVID)+ fileNames.get(FileCreater.PROPERTIES)
					+ fileNames.get(FileCreater.POPULATION)+ " User input: " + input);
					}
				ui.printAdditionalFeature(input);
				break;

			default:
				System.out.println("invlid, try again");
				break;
			}
			System.out.println("Please enter any number within the availbe options, enter 0 to exist");
			choice = scanner.nextInt();
			choiceCheck  = false;
			while (!choiceCheck) {
				if (choice > 8) {
					System.out.println("You have to enter a number between 0 - 8");	
					choice = scanner.nextInt();
					continue;
					}
				
				boolean casesCheck = false;
				while (!casesCheck) {
					// check if the input is within the avalible options
					if (userOptions.contains(choice)) {
						casesCheck = true;
					}
					else {
						System.out.println("please enter the number within the availabe options");
						choice = scanner.nextInt();
					}
				}
				choiceCheck = true;
			}
			
			
		}
		logger.close();
		
		


		
		


		

		//testing speed <120000ms

		long endTime = System.currentTimeMillis();
		long elapsedTime = endTime - startTime;
		System.out.println("Elapsed time: " + elapsedTime + " milliseconds");
		
		


	}
}