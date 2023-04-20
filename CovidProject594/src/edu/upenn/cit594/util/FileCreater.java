package edu.upenn.cit594.util;

public class FileCreater {
	
	
	/**
	 * This method will create the files array with length of 4
	 * filenames [covid_data, properties_data, population_data, log_file]
	 * @param filenames
	 * @return a string array with length 4
	 */
	public static String[] createFiles (String[] filenames) {
		// assume the filenames are correct
		String[] res = new String[4];
		String [] prefix = {"--covid=", "--properties=", "--population=", "--log="};
		
		int index = 0;
			for (int j = 0; j < prefix.length; j++) {
				// if the file start with given index
				// if the index is greater than the filenames length
				if (index > filenames.length - 1) {
					res[j] = "";
				}
				// if the filenames starts with the prefix
				else if (filenames[index].startsWith(prefix[j])){
					res[j] = filenames[index].substring(prefix[j].length());
					index++;
					
				}
				// if it doesn't contain thing
				else {
					res[j] = "";
					

				}
				
			}
		
		
		
		return res;
	}
	
	

}
