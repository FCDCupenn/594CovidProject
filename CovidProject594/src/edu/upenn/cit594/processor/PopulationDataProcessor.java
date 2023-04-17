package edu.upenn.cit594.processor;

import java.util.List;

import edu.upenn.cit594.util.Population;

public class PopulationDataProcessor {
	
	
	/**
	 * This will return the number of total population count
	 * @param popluationList
	 * @return
	 */
	public static long totalPoplulation(List<Population> popluationList) {
		long count = 0;
		
		for(Population p : popluationList) {
			count = (long) (count + p.getPopulation());
		}
		
		return count;
		
	}

}
