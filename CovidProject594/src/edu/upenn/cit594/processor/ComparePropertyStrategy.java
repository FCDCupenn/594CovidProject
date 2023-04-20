package edu.upenn.cit594.processor;

public interface ComparePropertyStrategy {

    public boolean equals(String zipcode1, String zipcode2);


    boolean isNotMalformed(Double value);
}
