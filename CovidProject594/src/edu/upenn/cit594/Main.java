package edu.upenn.cit594;

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
		
		if (args.length > 4) {
	          System.err.println("incorrect number of arguments");
	          return;
	       }	
		
		
		String[] files = args;
		String[] finalFiles = null;
		// check if files are valid
//		if (Userinterface.checkValidFileNames(files)) {
//			finalFiles = FileCreater.createFiles(files);
//			for (int i = 0; i < finalFiles.length; i++) {
//				System.out.println(finalFiles[i]);
//			}
//		}
//		else {
//			System.out.println("file is not valid");
//			return;
//		}

		// intial reader
		AlmightyReader reader_final  = new AlmightyReader(finalFiles);
		CovidDataProcessor cdp_final = new CovidDataProcessor(reader_final);
		PropertyAnalyzer property_csv = new PropertyAnalyzer(reader_final);
		Userinterface ui = new Userinterface(property_csv, cdp_final);
		Logger logger = Logger.getInstance();
		logger.setLogFile(files[3]);


		Scanner scanner = new Scanner(System.in);
		int choice = 0;
		String input = null;
		String date = null; 
		
		
		// display the inital input
		Userinterface.printManu();

		do {
			choice = scanner.nextInt();
			switch (choice) {
			case 0: System.out.println("Exit!"); return;
			case 1:
				Userinterface.printAvailableActionsOptions(files);
			case 2:
			case 3:
				System.out.println("Type partial or full");
				input = scanner.next();
				if (input.equals("partial")) {
					date = scanner.next();
					System.out.println("please enter a date in the format: YYYY-MM-DD");
					if (Userinterface.checkDateFormat(date)) {
						Userinterface.printTotalPartialOrFullVacPerCapita(cdp_final.getpartialOrFullVacPerCapita(date, "partial"));
					}
					else {
						System.out.println("The date format is wrong, please enter a date in the format: YYYY-MM-DD");
					}

				}
				else if (input.equals("full")) {
					System.out.println("please enter a date in the format: YYYY-MM-DD");
					if (Userinterface.checkDateFormat(date)) {
						Userinterface.printTotalPartialOrFullVacPerCapita(cdp_final.getpartialOrFullVacPerCapita(date, "full"));
					}
					else {
						System.out.println("The date format is wrong, please enter a date in the format: YYYY-MM-DD");
					}

				}
				else
					System.out.println("Please Type partial or full");

			case 4:
				System.out.println("Please Type zipcode");
				input = scanner.next();
				if(input!=null){
					logger.log("opened file: " + args[1] + " User input: " + input + " result: " + ui.printAvgMarketValue(input));
					break;}
				else{
					break;}

				case 5:
					System.out.println("Please Type zipcode");
					input = scanner.next();
					if(input!=null){
					logger.log("opened file: " + args[1] + args[2] + " User input: " + input + " result: " + ui.printAvgTotalLivableArea(input));
					break;}
					else{
						break;}


				case 6:
					System.out.println("Please Type zipcode");
					input = scanner.next();
					if(input!=null){
						logger.log("opened file: " + args[1] + " User input: " + input + " resulet: " + ui.printValuePerCapita(input));
						break;}
					else{
						break;}

			case 7:

			default:
				System.out.println("invlid, try again");
				break;
			}
		}
		while (choice < 8);


		long startTime = System.currentTimeMillis();
		
		// construct the new file array input from the args

		
		// only for test.....
		String[] filenames_1 = {"covid_data.json", "properties.csv", "population.csv", "log_1"};
		String[] filenames_2 = {"covid_data.csv", "properties.csv", "population.csv", "log_2"};
		String[] filenames_3 = {"", "", "", "log_1"};
		
		AlmightyReader reader_json = new AlmightyReader(filenames_1);
		AlmightyReader reader_csv = new AlmightyReader(filenames_2);
		
		
		CovidDataProcessor cdp_json = new CovidDataProcessor(reader_json);
		CovidDataProcessor cdp_csv = new CovidDataProcessor(reader_csv);

		
		
		long totalPopulation = PopulationDataProcessor.totalPoplulation(reader_final.getPopulationList());
		
		Userinterface.printTotalPopulationForAllZipCodes(totalPopulation);
	
		
		 date = "2021-03-25";
		String partial = "partial";
		String full = "full";
		String zipcode ="19131";
//		System.out.println(
//		cdp_json.getpartialOrFullVacPerCapita(date, partial));
//		System.out.println(
//		cdp_csv.getpartialOrFullVacPerCapita(date, partial));
//		
		Userinterface.printTotalPartialOrFullVacPerCapita(cdp_json.getpartialOrFullVacPerCapita(date, partial));
		Userinterface.printTotalNegOrPosVacPerCapita(cdp_final.getTotalNegOrPosVacPerZipCodePerDatePerCapita(date, "negative"));
		Userinterface.printAvailableActionsOptions(filenames_3);


		
		// addtional feature 
		AdditionalFeatureProcessor afp = new AdditionalFeatureProcessor(cdp_final, property_csv);
		Userinterface.printAdditionalFeature(afp.getTotalHospitalizedAndTotalMarketValuePerCapita(zipcode));

		//testing speed <120000ms

		long endTime = System.currentTimeMillis();
		long elapsedTime = endTime - startTime;
		System.out.println("Elapsed time: " + elapsedTime + " milliseconds");
		
		

//for testing only
// 		System.out.println(property_csv.getHaspmap());

//		PropertyReader test2 =new PropertyReader("properties.csv");
//		List<Property> propertyDataList = test2.getPropertiesDataList();
//
//    for(int i =0;i<100;i++){
//       System.out.println(propertyDataList.get(i).toString());
//    }

	}
}