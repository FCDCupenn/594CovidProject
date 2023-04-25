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

		if (args.length < 1 || args.length > 4) {
			System.err.println("incorrect number of arguments");
			return;
		}

		long startTime = System.currentTimeMillis();

		String[] files = args;
		// check if all file names are correct
		if (!FileCreater.checkValidFileNames(files)) {
			System.err.println("file names are not valid");
			return;
		}
		
		// get final map for fileNames
		Map<String, String> fileNames = FileCreater.createFilesNames(files);
		
		// check if the file exists
		if (!FileCreater.checkFilesExist(fileNames)) {
			System.err.println("file names are not valid");
			return;
		}
			
		Logger logger = Logger.getInstance();
		;
		logger.setLogFile(fileNames.get(FileCreater.LOG));

		// intial reader

		AlmightyReader reader_final = new AlmightyReader(fileNames);

		CovidDataProcessor cdp_final = new CovidDataProcessor(reader_final);
		PropertyAnalyzer property_csv = new PropertyAnalyzer(reader_final);
		AdditionalFeatureProcessor afp = new AdditionalFeatureProcessor(cdp_final, property_csv);

		Userinterface ui = new Userinterface(property_csv, cdp_final, afp);

		// log commend line
		String argsline = "";
		for (int i = 0; i < args.length; i++) {
			argsline += args[i] + " ";
		}
		logger.log(argsline);

		Scanner scanner = new Scanner(System.in);

		int choice = -1;

		List<Integer> userOptions = ui.getAvailableActionsOptions(fileNames);
		String input = null;
		String date = null;

		// display the inital input
		ui.printManu();
		while (choice < 0 || choice > 7) {
			System.out.println("Enter a number between 0-7\n> ");
			if (!scanner.hasNextInt()) {
				System.out.println("Please enter valid number\n> ");
				scanner.nextLine();
				continue;
			}
			choice = scanner.nextInt();
			if (!userOptions.contains(choice)) {
				System.out.println("please enter a number within the given options\n> ");
				choice = -1;
			}
		}

		while (choice < 8) {

			switch (choice) {
			case 0:
				System.out.println("Exit!");
				return;
			case 1:

				ui.printAvailableActionsOptions(fileNames);
				break;
			case 2:

				logger.log(fileNames.get(FileCreater.POPULATION));
				ui.printTotalPopulationForAllZipCodes();
				break;
			case 3:

				System.out.println("Type partial or full\n> ");
				input = scanner.next();
				if (input.equals("partial")) {
					System.out.println("please enter a date in the format: YYYY-MM-DD\n> ");
					date = scanner.next();

					if (ui.checkDateFormat(date)) {

						logger.log(fileNames.get(FileCreater.COVID));
						ui.printTotalPartialOrFullVacPerCapita(date, "partial");

					} else {
						System.out
								.println("The date format is wrong, please enter a date in the format: YYYY-MM-DD\n> ");
						break;
					}

				} else if (input.equals("full")) {
					System.out.println("please enter a date in the format: YYYY-MM-DD\n> ");
					date = scanner.next();
					if (ui.checkDateFormat(date)) {

						logger.log(fileNames.get(FileCreater.COVID));

						ui.printTotalPartialOrFullVacPerCapita(date, "full");
					} else {
						System.out
								.println("The date format is wrong, please enter a date in the format: YYYY-MM-DD\n>");
						break;
					}

				} else
					System.out.println("Please Type partial or full\n> ");
				break;

			case 4:

				System.out.println("Please Type 5 digit zipcode\n> ");
				input = scanner.next();
				if (input != null) {
					if (input.length() != 5) {
						System.out.println("Please Type exactly 5 digit zipcode\n> ");
						break;
					}
					ui.printAvgMarketValue(input);
					logger.log(fileNames.get(FileCreater.PROPERTIES));

				}
				break;

			case 5:

				System.out.println("Please Type 5 digit zipcode\n> ");
				input = scanner.next();
				if (input != null) {
					if (input.length() != 5) {
						System.out.println("Please Type exactly 5 digit zipcode\n> ");
						break;
					}
					ui.printAvgTotalLivableArea(input);
					logger.log(fileNames.get(FileCreater.PROPERTIES));

				}
				break;

			case 6:

				System.out.println("Please Type 5 digit zipcode\n> ");
				input = scanner.next();
				if (input != null) {
					if (input.length() != 5) {
						System.out.println("Please Type exactly 5 digit zipcode\n> ");
						break;
					}
					ui.printValuePerCapita(input);
					logger.log(fileNames.get(FileCreater.PROPERTIES) + " " + fileNames.get(FileCreater.POPULATION));

				}
				break;

			case 7:

				System.out.println("Please Type 5 digit zipcode\n> ");
				input = scanner.next();
				if (input != null) {

					if (input.length() != 5) {
						System.out.println("Please Type exactly 5 digit zipcode\n> ");
						break;
					}
					logger.log(fileNames.get(FileCreater.COVID) + fileNames.get(FileCreater.PROPERTIES)
							+ fileNames.get(FileCreater.POPULATION));
				}
				ui.printAdditionalFeature(input);
				break;

			default:
				System.out.println("invlid, try again\n> ");
				break;
			}
			// System.out.println("Please enter any number within the availbe options, enter
			// 0 to exist \n > ");
			choice = -1;
			while (choice < 0 || choice > 7) {
				System.out.println("Enter a number between 0-7 to restart the program\n> ");
				if (!scanner.hasNextInt()) {
					System.out.println("Please enter valid number\n> ");
					scanner.nextLine();
					continue;
				}
				choice = scanner.nextInt();
				if (!userOptions.contains(choice)) {
					System.out.println("please enter a number with given options\n> ");
					choice = -1;
				}
			}

		}
		logger.close();

		// testing speed <120000ms

		long endTime = System.currentTimeMillis();
		long elapsedTime = endTime - startTime;
		System.out.println("Elapsed time: " + elapsedTime + " milliseconds");

	}
}