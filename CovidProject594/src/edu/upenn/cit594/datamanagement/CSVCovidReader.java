package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.util.Covid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CSVCovidReader {
	
	 public CSVReader csvReader;
	 private Map<String, Integer> headerID;
	 private List<Covid> covidDataList = new ArrayList<>();
	 
	 public CSVCovidReader() {}

	public CSVCovidReader(String filename) throws IOException {
		try( var reader = new CharacterReader(filename)){
			csvReader = new CSVReader(reader);
			headerID =csvReader.generateHeaderID();
			String[] row;
			while((row =csvReader.readRow())!=null){
				String zipCode = row[headerID.get("zip_code")];
				String timestamp = row[headerID.get("etl_timestamp")];
				long partiallyVaccinated = parseStringtoInt(row[headerID.get("partially_vaccinated")]);
				long fullyVaccinated = parseStringtoInt(row[headerID.get("fully_vaccinated")]);
				long NEG = parseStringtoInt(row[headerID.get("NEG")]);
				long POS = parseStringtoInt(row[headerID.get("POS")]);
				long deaths = parseStringtoInt(row[headerID.get("deaths")]);
				long hospitalized = parseStringtoInt(row[headerID.get("hospitalized")]);
				long boosted = parseStringtoInt(row[headerID.get("boosted")]);

				//skip invalid digit
				if(zipCode.length() != 5){
					continue;
				}
				if(!csvReader.checkTimeFormat(timestamp)){
					continue;
				}
				Covid eachCovidRecord = new Covid(zipCode,NEG, POS, deaths, hospitalized,partiallyVaccinated,fullyVaccinated, boosted,timestamp);
				covidDataList.add(eachCovidRecord);
			}
			//System.out.println("Covid data List size:"+ covidDataList.size());

		} catch (CSVFormatException e) {
			throw new RuntimeException(e);
		}


	 }

	 private long parseStringtoInt(String text){
		 if(text.isEmpty()){
			 return 0;
		 }else{
			 return Integer.parseInt(text);
		 }
	 }

	public List<Covid> getCovidDataList() {
		return covidDataList;
	}
}


