package edu.upenn.cit594.util;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileCreater {

	public final static String COVID = "--covid";
	public final static String PROPERTIES = "--properties";
	public final static String POPULATION = "--population";
	public final static String LOG = "--log";
	
	
	
	public final static Set<String> prefixes = new HashSet<>(
			Arrays.asList(COVID, PROPERTIES, POPULATION, LOG));

	/**
	 * This method will create the files array with length of 4 filenames
	 * [covid_data, properties_data, population_data, log_file]
	 * 
	 * @param args
	 * @return a string array with length 4
	 */
	public static Map<String, String> createFilesNames(String[] args) {
		Map<String, String> res = new HashMap<>();

		// if all files are valid
		// create new files
		for (String arg : args) {
			if (arg.contains("=")) {
				String[] pair = arg.split("=");
				if (pair.length != 2)
					continue;
				if (prefixes.contains(pair[0])) {
					res.put(pair[0], pair[1]);
				}
			}
		}

		return res;
	}

	/**
	 * this method will check if all input files are valid
	 * 
	 * @param filenames
	 * @return return true if the file is valid
	 */
	public static boolean checkValidFileNames(String[] filenames) {

		String[] prefix = { "--covid=", "--properties=", "--population=", "--log=" };
		// check format
		String regex = "^--(?<name>.+?)=(?<value>.+)$";
		Pattern p1 = Pattern.compile(regex);
		for (String s : filenames) {
			Matcher m1 = p1.matcher(s);
			// if it matches
			if (m1.find()) {

				// check the name argument
				int count = 0;
				for (int i = 0; i < prefix.length; i++) {
					
					if (s.startsWith(prefix[i])) {
//							// if the file contain the prefix, check if this file exist
//							
//							File file = new File(s.substring(prefix[i].length()));

						// need to check if there is a duplicate
						// the index will always start at 0
						int index = s.substring(prefix[i].length() - 1).indexOf(prefix[i]);
						// if there is a replicate, then index will not be -1
						// so it will return false
						if (index != -1) {
							return false;
						}
						else {
							break;
						}

					}
					else {
						count += 1;
						// if count ==4 means that it checks all the files and still can't find it
						if (count == 4) return false;
					}
				}

			} 
			else {
				return false;
			}
		}
		return true;
	}

	public static boolean checkFilesExist(Map<String, String> filenames) {

		for (Map.Entry<String, String> filename : filenames.entrySet()) {
			// check if the file exist or not
			String name = filename.getValue();

			File file = new File(name);
			if (!file.exists()) {
				return false;
			}
		}

		return true;

	}

}
