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

	public CSVCovidReader(String filename) throws IOException {
		try( var reader = new CharacterReader(filename)){
			csvReader = new CSVReader(reader);
			headerID =csvReader.generateHeaderID();
			String[] row;
			while((row =csvReader.readRow())!=null){
				String zipCode = row[headerID.get("zip_code")];
				String timestamp = row[headerID.get("etl_timestamp")];
				int partiallyVaccinated = parseStringtoInt(row[headerID.get("partially_vaccinated")]);
				int fullyVaccinated = parseStringtoInt(row[headerID.get("fully_vaccinated")]);
				int NEG = parseStringtoInt(row[headerID.get("NEG")]);
				int POS = parseStringtoInt(row[headerID.get("POS")]);
				int deaths = parseStringtoInt(row[headerID.get("deaths")]);
				int hospitalized = parseStringtoInt(row[headerID.get("hospitalized")]);
				int boosted = parseStringtoInt(row[headerID.get("boosted")]);

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
			System.out.println("Covid data List size:"+ covidDataList.size());

		} catch (CSVFormatException e) {
			throw new RuntimeException(e);
		}


	 }

	 private int parseStringtoInt(String text){
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


