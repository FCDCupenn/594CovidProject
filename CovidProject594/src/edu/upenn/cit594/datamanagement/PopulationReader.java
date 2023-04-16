package edu.upenn.cit594.datamanagement;

import edu.upenn.cit594.util.Population;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PopulationReader {

    public CSVReader csvReader;
    private Map<String, Integer> headerID;
    private List<Population> populationDataList = new ArrayList<>();

    public  PopulationReader (String filename) throws IOException {
        try( var reader = new CharacterReader(filename)){
            csvReader = new CSVReader(reader);
            headerID =csvReader.generateHeaderID();
            String[] row;
            while((row =csvReader.readRow())!=null){
                String zipCode = row[headerID.get("zip_code")];
                int population;
                try {
                    population = Integer.parseInt(row[headerID.get("population")]);
                } catch (NumberFormatException e) {
                    continue;
                }
                //skip invalid digit
                if(zipCode.length() != 5){
                    continue;
                }

                Population populationData = new Population(population,zipCode);
                populationDataList.add(populationData);
            }
            System.out.println("Covid data List size:"+ populationDataList.size());

        } catch (CSVFormatException e) {
            throw new RuntimeException(e);
        }


    }

    public List<Population> getPopulationDataList() {
        return populationDataList;
    }

}
