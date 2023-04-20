package edu.upenn.cit594.processor;

import edu.upenn.cit594.datamanagement.AlmightyReader;
import edu.upenn.cit594.util.Population;
import edu.upenn.cit594.util.Property;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PropertyAnalyzer {

    private final AlmightyReader reader;
    private final List<Property> propertyDataSet;
    private final List<Population> populationDataSet;

    private Map<String, Integer> avgMaketValueStorage = new HashMap<>();
    private Map<String, Integer> avgLivableAreaStorage = new HashMap<>();
    private Map<String, Integer> avgValuePerCapitaStorage = new HashMap<>();

    private ComparePropertyValue compareValue =new ComparePropertyValue();
    public PropertyAnalyzer (AlmightyReader reader) {
        this.reader = reader;
        propertyDataSet = reader.getPropertyList();
        populationDataSet =reader.getPopulationList();
    }





    public int getAverageMarketValue( String inputZipcode) {
        if(avgMaketValueStorage.containsKey(inputZipcode)){
            return avgMaketValueStorage.get(inputZipcode);
        }
        double totalMarketValue = 0;
        int numOfProperty = 0;
        for (Property property : propertyDataSet) {
            if (compareValue.equals(inputZipcode,property.getZipCode())) {
                if(compareValue.isNotMalformed(property.getMarketValue())){
                totalMarketValue += property.getMarketValue();
                numOfProperty++;
            }
        }}
        if (numOfProperty == 0) {
            return 0;
        }
        int avgMarketValue =(int) Math.floor(totalMarketValue/numOfProperty);
        avgMaketValueStorage.put(inputZipcode, avgMarketValue);
        return avgMarketValue;    }


    public int getAverageLivableArea( String inputZipcode) {
        if(avgLivableAreaStorage.containsKey(inputZipcode)){
            return avgLivableAreaStorage.get(inputZipcode);
        }
        double totallivableArea= 0;
        int numOfProperty = 0;
        for (Property property : propertyDataSet) {
            if (compareValue.equals(inputZipcode,property.getZipCode())) {
                if(compareValue.isNotMalformed(property.getTotalLivableArea())){
                    totallivableArea += property.getTotalLivableArea();
                    numOfProperty++;
                }
            }}
        if (numOfProperty == 0) {
            return 0;
        }
        int avgreLivableArea =(int) Math.floor(totallivableArea/numOfProperty);
        avgMaketValueStorage.put(inputZipcode, avgreLivableArea);
        return avgreLivableArea;
    }

    public int getATotalMarketValuePerCapita( String inputZipcode) {
        if(avgValuePerCapitaStorage.containsKey(inputZipcode)){
            return avgValuePerCapitaStorage.get(inputZipcode);
        }
        double totalMarketValue= 0;
        int totalPopulationInArea= 0;

        for (Property property : propertyDataSet) {
            if (compareValue.equals(inputZipcode, property.getZipCode())) {
                if (compareValue.isNotMalformed(property.getMarketValue())) {
                    totalMarketValue += property.getMarketValue();
                }
            }
        }
            for (Population population : populationDataSet) {
                if(compareValue.equals(inputZipcode,population.getZipCode())){
                    totalPopulationInArea = population.getPopulation();
                }
            }

        if (totalMarketValue == 0 || totalPopulationInArea ==0) {
            return 0;
        }

        int avgValuePerCpita =(int) Math.floor(totalMarketValue/totalPopulationInArea);
        avgValuePerCapitaStorage.put(inputZipcode, avgValuePerCpita);
        return avgValuePerCpita;    }

}
