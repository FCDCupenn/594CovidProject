package edu.upenn.cit594.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Userinterface {
	
	

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
				// check if the file existed 
				File file = new File(s);
				if(file.exists()) {
					// if the file exists
					// check the name argument
					for (int i = 0; i< prefix.length; i++) {
						if (s.startsWith(prefix[i])) {
							// if the file contain the prefix, need to check if there is a duplicate
							
							// the index will always start at 0
							int index = s.substring(prefix[i].length()-1).indexOf(prefix[i]);
							// if there is a replicate, then index will not be -1
							// so it will return false
							if (index != -1) return false;
						}
					}
									
				}
				else {
					return false;
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
		List<Integer> options = new ArrayList<>();
		options = getAvailableActionsOptions(filenames);
		System.out.println("BEGIN OUTPUT");
		for (int i = 0; i < options.size(); i++) {
			System.out.println(options.get(i));
		}
		System.out.println("END OUTPUT");
		
	}
	
	
	
	
	
}
