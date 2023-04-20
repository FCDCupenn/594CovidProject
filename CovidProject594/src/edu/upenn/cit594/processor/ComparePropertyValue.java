package edu.upenn.cit594.processor;

public class ComparePropertyValue implements ComparePropertyStrategy {
    @Override
    public boolean equals(String zipcode1, String zipcode2) {
        return zipcode1.equals(zipcode2);
    }


    @Override
    public boolean isNotMalformed(Double value) {
        return value != Double.NEGATIVE_INFINITY;

    }
}