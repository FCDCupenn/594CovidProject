package edu.upenn.cit594.datamanagement;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import edu.upenn.cit594.util.Covid;
public class JSONFileReader {
	
	
	
	final static String ZIP_CODE = "zip_code";
	final static String NEG = "NEG";
	final static String POS = "POS";
	final static String DEATH = "deaths";
	final static String HOSPITALIZED = "hospitalized";
	final static String PARTIALVAC = "partially_vaccinated";
	final static String FULLYVAC = "fully_vaccinated";
	final static String BOOSTER = "boosted";
	final static String ETL_TIMESTAMP = "etl_timestamp";
	String[] columnHeader = {ZIP_CODE, NEG, POS, DEATH, HOSPITALIZED, PARTIALVAC, FULLYVAC, BOOSTER, ETL_TIMESTAMP};
	

	public JSONFileReader() {
		
	}
	/**
	 * this method will return a JSONObject from reading the file
	 * @return return all Covid in a list
	 * 
	 */
	public  List<Covid> readAllCovid(String filename) {
		List<Covid> res = new ArrayList<>();
		JSONArray jo = null;
		try {
			
			Object obj = new JSONParser().parse(new FileReader(filename));
			jo = (JSONArray) obj;
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		res = this.processCovidData(jo);
		return res;
		
	}
	
	/**
	 * This method will get the Tweet information from the read file
	 * @return all tweet information from the JSONArray
	 */
	
	private List<Covid> processCovidData(JSONArray ja) {
		List<Covid> res = new ArrayList<>();

		Iterator<?> itr = ja.iterator();
		while (itr.hasNext()) {
			JSONObject jo = (JSONObject) itr.next();
			ArrayList<Long> covidContent = new ArrayList<>();
			// if the zipcode is missing put it as empty string
			String zipcode = jo.containsKey(ZIP_CODE) ? String.valueOf(jo.get(ZIP_CODE)): "";
			if(zipcode.length() != 5){
				continue;
			}
			for (int i = 1; i < columnHeader.length - 1; i++) {
				long covidInfo = jo.containsKey(columnHeader[i]) ? (Long) jo.get(columnHeader[i]) : 0;
				covidContent.add(covidInfo);

			}
			
			//empty String
			String date = jo.containsKey(ETL_TIMESTAMP) ? ((String) jo.get(ETL_TIMESTAMP)) : "";


			Covid covid = new Covid(zipcode, covidContent.get(0), covidContent.get(1), covidContent.get(2),
					covidContent.get(3), covidContent.get(4), covidContent.get(5), covidContent.get(6), date);

			res.add(covid);
		}
		return res;
	}
	
	
	

	
	
}
