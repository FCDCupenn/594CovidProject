package edu.upenn.cit594.datamanagement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.upenn.cit594.util.Covid;
import edu.upenn.cit594.util.FileCreater;
import edu.upenn.cit594.util.Population;
import edu.upenn.cit594.util.Property;

public class AlmightyReader {

	/**
	 * This will read covid information
	 */
	public Map<String, String> fileNames;

	public AlmightyReader(Map<String, String> fileNames) {
		this.fileNames = fileNames;
	};

	/**
	 * This method will get a covid data in List
	 * 
	 * @param filename
	 * @return List of Covid object
	 * @throws IOException
	 */
	public List<Covid> getCovidList() {
		List<Covid> covidList = new ArrayList<>();
		if (!fileNames.containsKey(FileCreater.COVID)) {
			return covidList;
		}
		// read covid file
		String covidfile = fileNames.get(FileCreater.COVID);
		// end with text file
		if (covidfile.toLowerCase().endsWith(".csv")) {
			try {
				covidList = new CSVCovidReader(covidfile).getCovidDataList();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// end with json file
		else if (covidfile.toLowerCase().endsWith(".json")) {
			covidList = new JSONFileReader().readAllCovid(covidfile);
		} else
			System.err.println("The covid file time is wrong");

		return covidList;

	}

	/**
	 * will return a list of property
	 * 
	 * @return a list of property
	 */
	public List<Property> getPropertyList() {
		List<Property> propertyList = new ArrayList<>();
		if (!fileNames.containsKey(FileCreater.PROPERTIES)) {
			return propertyList;
		}

		String propertyfile = fileNames.get(FileCreater.PROPERTIES);
		if (propertyfile.toLowerCase().endsWith(".csv")) {
			try {
				propertyList = new PropertyReader(propertyfile).getPropertiesDataList();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else
			System.err.println("The property file time is wrong");
		return propertyList;

	}

	/**
	 * will return a list of population
	 * 
	 * @return a list of population
	 */
	public List<Population> getPopulationList() {
		List<Population> populationList = new ArrayList<>();
		if (!fileNames.containsKey(FileCreater.POPULATION)) {
			return populationList;
		}

		String populationFile = fileNames.get(FileCreater.POPULATION);

		if (populationFile.toLowerCase().endsWith(".csv")) {
			try {
				populationList = new PopulationReader().getPopulationDataList(populationFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else
			System.err.println("The population file time is wrong");
		return populationList;

	}
	
	/**
	 * this method will convert populationList to a Map for computing
	 * @param populationList
	 * @return a map contains the same thing but in map
	 */
	public Map<String, Long> populationListToMap(List<Population> populationList) {	
		Map<String, Long> res = new HashMap<>();
		 for(Population p : populationList) {
			 res.put(p.getZipCode(), (long) p.getPopulation());
		 }
		
		return res;
		
	}

}
