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
import edu.upenn.cit594.processor.CovidDataProcessor;
import edu.upenn.cit594.processor.PopulationDataProcessor;
import edu.upenn.cit594.processor.PropertyAnalyzer;
import edu.upenn.cit594.util.Covid;
import edu.upenn.cit594.util.Population;
import edu.upenn.cit594.util.Property;

public class Main {

	public static void main(String[] args) throws IOException {

//        if (args.length != 4) {
//            System.err.println("incorrect number of arguments");
//            return;
//        }

//		Scanner scanner = new Scanner(System.in);
//		int choice = 0;
//
//		do {
//			System.out.println(" Please Choose :\n" + "1.show all \n" + "2.show \n" + "3.show \n");
//			choice = scanner.nextInt();
//			switch (choice) {
//
//			case 1:
//			case 2:
//			case 3:
//			case 4:
//			case 5:
//			case 6:
//			case 7:
//
//			default:
//				System.out.println("invlid, try again");
//				break;
//			}
//		} while (choice < 8);
		
		// only for test.....
		String[] filenames_1 = {"covid_data.json", "properties.csv", "population.csv", "log_1"};
		String[] filenames_2 = {"covid_data.csv", "properties.csv", "population.csv", "log_2"};
		
		AlmightyReader reader_json = new AlmightyReader(filenames_1);
		AlmightyReader reader_csv = new AlmightyReader(filenames_2);
		
		CovidDataProcessor cdp_json = new CovidDataProcessor(reader_json);
		CovidDataProcessor cdp_csv = new CovidDataProcessor(reader_csv);
		
		
		String date = "2021-03-25";
		String partial = "partial";
		String full = "full";
		System.out.println(
		cdp_json.getpartialOrFullVacPerCapita(date, partial));
		System.out.println(
		cdp_csv.getpartialOrFullVacPerCapita(date, partial));
	}
}