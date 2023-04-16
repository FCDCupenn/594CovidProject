package edu.upenn.cit594;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import edu.upenn.cit594.datamanagement.CSVCovidReader;
import edu.upenn.cit594.datamanagement.CovidReader;
import edu.upenn.cit594.datamanagement.JSONFileReader;
import edu.upenn.cit594.datamanagement.PropertyReader;
import edu.upenn.cit594.processor.CovidDataProcessor;
import edu.upenn.cit594.processor.PropertyAnalyzer;
import edu.upenn.cit594.util.Covid;
import edu.upenn.cit594.util.Property;

public class Main {
	class Pair<K, V> {
		K k;
		V v;
		public Pair(K k, V v) {
			this.k = k;
			this.v = v;
		}
	}
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
		
		String filename = "covid_data.json";
		CovidReader cr = new CovidReader();
		
		List<Covid> c = cr.jReader.readAllCovid(filename);
//		System.out.println(c.get(84).getDate());
//		System.out.println(c.get(84).getNegTest());
		String date = "2021-03-25";
		final Map<Long, Long> populationMap = new HashMap<>();
		final Map<Long, Long> negs = c.stream().filter(e -> e.getDate().equals(date)).
			collect(Collectors.groupingBy(Covid::getZipCode, Collectors.summingLong(Covid::getNegTest)));
			
		
		System.out.println(negs);
		
		CovidDataProcessor cp = new CovidDataProcessor();
		cp.covidDataSet = c;
		Map<Long, Long> res = cp.totalPosVacPerZipCodePerDate(date);
		System.out.println(res);


		//testing only

		 CSVCovidReader test =new CSVCovidReader("covid_data.csv");
		        List<Covid> covidDataList = test.getCovidDataList();
		        System.out.println(covidDataList.size());

		//        for(int i =0;i<covidDataList.size();i++){
		//            System.out.println(covidDataList.get(i).toString());
		//        }

		        PropertyReader test2 =new PropertyReader("properties.csv");
		        List<Property> propertyDataList = test2.getPropertiesDataList();

		        for(int i =0;i<100;i++){
		            System.out.println(propertyDataList.get(i).toString());
		        }

		        System.out.println(propertyDataList.size());

		//        PopulationReader test3 =new PopulationReader("population.csv");
		//        List<Population> populationDataList = test3.getPopulationDataList();
		//
		//        for(int i =0;i<populationDataList.size();i++){
		//            System.out.println(populationDataList.get(i).toString());
		//        }
		//
		//        System.out.println(populationDataList.size());

		        PropertyAnalyzer propertyAnalyzer = new PropertyAnalyzer();
		        String zip = "19148";

		Double averageMarketValue = propertyAnalyzer.AverageMarketValue(propertyDataList, zip);

		System.out.println("The average market value for properties in " + zip + " is " + averageMarketValue);
		
	}
}