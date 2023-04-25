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
				long partiallyVaccinated = parseStringtoLong(row[headerID.get("partially_vaccinated")]);
				long fullyVaccinated = parseStringtoLong(row[headerID.get("fully_vaccinated")]);
				long NEG = parseStringtoLong(row[headerID.get("NEG")]);
				long POS = parseStringtoLong(row[headerID.get("POS")]);
				long deaths = parseStringtoLong(row[headerID.get("deaths")]);
				long hospitalized = parseStringtoLong(row[headerID.get("hospitalized")]);
				long boosted = parseStringtoLong(row[headerID.get("boosted")]);

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


	 private Long parseStringtoLong(String text){

		 if(text.isEmpty()){
			 return 0L;
		 }else{
			 return Long.parseLong(text);
		 }
	 }

	public List<Covid> getCovidDataList() {
		return covidDataList;
	}
}


