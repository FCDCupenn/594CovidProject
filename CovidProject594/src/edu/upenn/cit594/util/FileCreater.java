package edu.upenn.cit594.util;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileCreater {
	

	
	/**
	 * This method will create the files array with length of 4
	 * filenames [covid_data, properties_data, population_data, log_file]
	 * @param filenames
	 * @return a string array with length 4
	 */
	public static String[] createFilesNames(String[] filenames) {
		String[] res = new String[4];
		String[] prefix = { "--covid=", "--properties=", "--population=", "--log=" };
		
			// if all files are valid
			// create new files
			int index = 0;
			for (int j = 0; j < prefix.length; j++) {
				// if the file start with given index
				// if the index is greater than the filenames length
				if (index > filenames.length - 1) {
					res[j] = "";
				}
				// if the filenames starts with the prefix
				else if (filenames[index].startsWith(prefix[j])) {
					res[j] = filenames[index].substring(prefix[j].length());
					index++;

				}
				// if it doesn't contain thing
				else {
					res[j] = "";

				}

			}
		// create a new filename for log 
		if (res[3] == "") {
			res[3] = "new_log_file.txt";
		}	
		
		return res;
	}
	
	/**
	 * this method will check if all input files are valid
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

				// if the file exists
				// check the name argument
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
						if (index != -1)
							return false;

					}
				}

			} else {
				return false;
			}
		}
		return true;
	}
	
	public static boolean checkFilesExist(String[] filenames) {
	
		for(int i = 0; i < filenames.length - 1; i++) {
			// check if the file exist or not
			if (filenames[i] != "") {
				File file = new File(filenames[i]);
				if (!file.exists()) {
					return false;
				}
			}
			
		}
		
		return true;
		
	}
	
	
	

}
