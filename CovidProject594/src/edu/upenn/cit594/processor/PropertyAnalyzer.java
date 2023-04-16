package edu.upenn.cit594.processor;

import edu.upenn.cit594.util.Property;

import java.util.List;

public class PropertyAnalyzer implements CompareZipCode {

    private CompareZipCode compareZipCode;

    @Override
    public boolean equals(String zipcode1, String zipcode2) {
        return true;
    }


    public double AverageMarketValue(List<Property> propertyList, String inputZipcode) {
        double totalMarketValue = 0;
        double numOfProperty = 0;
        for (Property property : propertyList) {
            if (equals(property.getZipCode(), inputZipcode)) {
                totalMarketValue += property.getMarketValue();
                numOfProperty++;
            }
        }
        return totalMarketValue/numOfProperty;
    }}
