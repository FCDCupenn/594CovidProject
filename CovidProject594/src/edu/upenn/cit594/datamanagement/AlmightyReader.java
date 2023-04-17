package edu.upenn.cit594.datamanagement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.upenn.cit594.util.Covid;
import edu.upenn.cit594.util.Population;
import edu.upenn.cit594.util.Property;

public class AlmightyReader {

	/**
	 * This will read covid information
	 */
	public String filenames[];

	public AlmightyReader(String[] filenames) {
		this.filenames = filenames;
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
		// read covid file
		String covidfile = this.filenames[0];
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
			System.out.println("The covid file time is wrong");

		return covidList;

	}

	/**
	 * will return a list of property
	 * 
	 * @return a list of property
	 */
	public List<Property> getPropertyList() {
		List<Property> propertyList = new ArrayList<>();

		String propertyfile = this.filenames[1];
		if (propertyfile.toLowerCase().endsWith(".csv")) {
			try {
				propertyList = new PropertyReader(propertyfile).getPropertiesDataList();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else
			System.out.println("The property file time is wrong");
		return propertyList;

	}

	/**
	 * will return a list of population
	 * 
	 * @return a list of population
	 */
	public List<Population> getPopulationList() {
		List<Population> populationList = new ArrayList<>();

		String populationFile = this.filenames[2];

		if (populationFile.toLowerCase().endsWith(".csv")) {
			try {
				populationList = new PopulationReader().getPopulationDataList(populationFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else
			System.out.println("The population file time is wrong");
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
