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
		String[] finalFiles = FileCreater.createFilesNames(files);
		Logger logger = Logger.getInstance();;
		 //check if files are valid
		try {
			if (FileCreater.checkFilesExist(finalFiles)) {
				// create log files
				File checkLogFile = new File(finalFiles[3]);
				if (!checkLogFile.exists())
				logger.setLogFile(finalFiles[3]);
				
			}
			else {
				System.out.println("file is not valid");
				return;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// intial reader
		AlmightyReader reader_final  = new AlmightyReader(finalFiles);
		CovidDataProcessor cdp_final = new CovidDataProcessor(reader_final);
		PropertyAnalyzer property_csv = new PropertyAnalyzer(reader_final);
		AdditionalFeatureProcessor afp = new AdditionalFeatureProcessor(cdp_final, property_csv);
		Userinterface ui = new Userinterface(property_csv, cdp_final, afp);
		
		
		
		


		Scanner scanner = new Scanner(System.in);
		int choice = 0;
		boolean choiceCheck = false;
		List<Integer> userOptions = ui.getAvailableActionsOptions(finalFiles);
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
				ui.printAvailableActionsOptions(finalFiles);
				break;			
			case 2:
				ui.printTotalPopulationForAllZipCodes();
				break;
			case 3:
				System.out.println("Type partial or full");
				input = scanner.next();
				if (input.equals("partial")) {
					System.out.println("please enter a date in the format: YYYY-MM-DD");
					date = scanner.next();
					
					if (ui.checkDateFormat(date)) {
						ui.printTotalPartialOrFullVacPerCapita(date, "partial");
					}
					else {
						System.out.println("The date format is wrong, please enter a date in the format: YYYY-MM-DD");
						break;
					}

				}
				else if (input.equals("full")) {
					System.out.println("please enter a date in the format: YYYY-MM-DD");
					if (ui.checkDateFormat(date)) {
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
				System.out.println("Please Type zipcode");
				input = scanner.next();
				if(input!=null){
					logger.log("opened file: " + finalFiles[1] + " User input: " + input + " result: " + ui.printAvgMarketValue(input));
					break;}
				else{
					break;}

				case 5:
					System.out.println("Please Type zipcode");
					input = scanner.next();
					if(input!=null){
					logger.log("opened file: " + finalFiles[1] + finalFiles[2] + " User input: " + input + " result: " + ui.printAvgTotalLivableArea(input));
					break;}
					else{
						break;}


				case 6:
					System.out.println("Please Type zipcode");
					input = scanner.next();
					if(input!=null){
						logger.log("opened file: " + finalFiles[1] + " User input: " + input + " result: " + ui.printValuePerCapita(input));
						break;}
					else{
						break;}

			case 7: 
				System.out.println("Please Type zipcode");
				input = scanner.next();
				if(input!=null){
					logger.log("opened file: " + finalFiles[1] + " User input: " + input);
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
		


		
		


		

		//testing speed <120000ms

		long endTime = System.currentTimeMillis();
		long elapsedTime = endTime - startTime;
		System.out.println("Elapsed time: " + elapsedTime + " milliseconds");
		
		


	}
}